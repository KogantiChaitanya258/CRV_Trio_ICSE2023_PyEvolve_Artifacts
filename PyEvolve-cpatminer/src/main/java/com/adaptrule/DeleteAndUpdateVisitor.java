package com.adaptrule;

import org.python.antlr.PythonTree;
import org.python.antlr.Visitor;
import org.python.antlr.ast.Module;
import org.python.antlr.ast.*;
import org.python.antlr.base.stmt;
import org.python.core.PyObject;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DeleteAndUpdateVisitor extends Visitor {
    java.util.List<PythonTree> del;
    PythonTree finalDel;
    Module rhs;
    public DeleteAndUpdateVisitor(java.util.List<PythonTree> deletes, PythonTree finalDeletedNode, Module rhs) {
        this.del=deletes;
        this.finalDel=finalDeletedNode;
        this.rhs=rhs;
    }


    @Override
    public void preVisit(PyObject node) {

    }

    @Override
    public void postVisit(PyObject node) {

    }

    private void removeChild(PythonTree node){

    }

    @Override
    public Object visitAssign(Assign node) throws Exception {
        for (PythonTree tree : del) {
            if (node.getInternalValue()==tree && finalDel==node.getInternalValue()){
                if(rhs.getInternalBody().get(0) instanceof Expr){
                    node.setValue(((Expr)rhs.getInternalBody().get(0)).getInternalValue());
                }

            }
            if (node.getChildren().remove(tree)) {
                int location = node.getChildren().indexOf(tree);
                if (location != -1) {
                    node.getChildren().add(location,rhs);
                }
            }
        }
        return super.visitAssign(node);
    }

    @Override
    public Object visitModule(Module node) throws Exception {
        for (PythonTree tree : del) {
            int location = -1;
            if (tree==finalDel && node.getInternalBody().contains(tree)){
                location =  node.getInternalBody().indexOf(tree);
            }
            node.getInternalBody().remove(tree);
            node.getChildren().remove(tree);
            if (location!=-1){
                for (stmt stmt : rhs.getInternalBody()) {
                    node.getInternalBody().add(location,stmt);
                    location+=1;
                }
            }
        }
        return super.visitModule(node);
    }

    @Override
    public Object visitInteractive(Interactive node) throws Exception {


        return super.visitInteractive(node);
    }

    @Override
    public Object visitExpression(Expression node) throws Exception {
        for (PythonTree tree : del) {
            int location = -1;
            if (node.getInternalBody().equals(tree)){
                node.setBody(tree);
            }
            location =  node.getChildren().indexOf(tree);
            node.getChildren().remove(tree);
            if (location!=-1){
                node.getChildren().add(location,tree);
            }
        }
        return super.visitExpression (node);
    }

    @Override
    public Object visitExpr(Expr  node) throws Exception{
        for (PythonTree tree : del) {
            if(node.getInternalValue()==tree){
                node.setValue(tree);
            }
            if (node.getChildren()!=null&&node.getChildren().contains(tree)){
                int location =  node.getChildren().indexOf(tree);
                if (location!=-1){
                    node.getChildren().add(location,tree);
                }
            }
        }
        return super.visitExpr(node);
    }


    @Override
    public Object visitSuite(Suite node) throws Exception {

        return super.visitSuite (node);
    }

    @Override
    public Object visitFunctionDef(FunctionDef node) throws Exception {
        for (PythonTree tree : del) {
            int location = -1;
            if (tree==finalDel && node.getInternalBody().contains(tree)){
                location =  node.getInternalBody().indexOf(tree);
            }
            node.getInternalBody().remove(tree);
            node.getChildren().remove(tree);
            if (location!=-1){
                for (stmt stmt : rhs.getInternalBody()) {
                    node.getInternalBody().add(location,stmt);
                    location+=1;
                }
            }
            java.util.List<stmt> deletables = new ArrayList<>();
            for (stmt stmt : node.getInternalBody()) {
                if (Util.isChildNode(tree, stmt)&&!Util.isChildNode(finalDel, stmt)){
                    deletables.add(stmt);
                }
            }


            for (stmt stmt : deletables) {
                if (stmt instanceof Assign)
                    node.getInternalBody().remove(stmt);
            }
        }
        return super.visitFunctionDef (node);
    }

    @Override
    public Object visitAsyncFunctionDef(AsyncFunctionDef node) throws Exception {
        for (PythonTree tree : del) {
            int location = -1;
            if (tree==finalDel && node.getInternalBody().contains(tree)){
                location =  node.getInternalBody().indexOf(tree);
            }
            node.getInternalBody().remove(tree);
            node.getChildren().remove(tree);
            if (location!=-1){
                for (stmt stmt : rhs.getInternalBody()) {
                    node.getInternalBody().add(location,stmt);
                    location+=1;
                }
            }
        }
        return super.visitAsyncFunctionDef (node);
    }

    @Override
    public Object visitClassDef(ClassDef node) throws Exception {

        return super.visitClassDef (node);
    }

    @Override
    public Object visitReturn(Return node) throws Exception {

        return super.visitReturn (node);
    }

    @Override
    public Object visitDelete(Delete node) throws Exception {

        return super.visitDelete (node);
    }

    @Override
    public Object visitAugAssign(AugAssign node) throws Exception {

        return super.visitAugAssign (node);
    }

    @Override
    public Object visitFor(For node) throws Exception {
        for (PythonTree tree : del) {
            int location = -1;
            if (tree==finalDel && node.getInternalBody().contains(tree)){
                location =  node.getInternalBody().indexOf(tree);
            }
            node.getInternalBody().remove(tree);
            node.getChildren().remove(tree);
            if (location!=-1){
                for (stmt stmt : rhs.getInternalBody()) {
                    node.getInternalBody().add(location,stmt);
                    location+=1;
                }
            }
        }
        return super.visitFor(node);
    }

    @Override
    public Object visitAsyncFor(AsyncFor node) throws Exception {
        for (PythonTree tree : del) {
            int location = -1;
            if (tree==finalDel && node.getInternalBody().contains(tree)){
                location =  node.getInternalBody().indexOf(tree);
            }
            node.getInternalBody().remove(tree);
            node.getChildren().remove(tree);
            if (location!=-1){
                for (stmt stmt : rhs.getInternalBody()) {
                    node.getInternalBody().add(location,stmt);
                    location+=1;
                }
            }
        }
        return super.visitAsyncFor(node);

    }

    @Override
    public Object visitWhile(While node) throws Exception {
        for (PythonTree tree : del) {
            int location = -1;
            if (tree==finalDel && node.getInternalBody().contains(tree)){
                location =  node.getInternalBody().indexOf(tree);
            }
            node.getInternalBody().remove(tree);
            node.getChildren().remove(tree);
            if (location!=-1){
                for (stmt stmt : rhs.getInternalBody()) {
                    node.getInternalBody().add(location,stmt);
                    location+=1;
                }
            }
        }
        return super.visitWhile (node);
    }

    @Override
    public Object visitIf(If node) throws Exception {
        for (PythonTree tree : del) {
            int location = -1;
            if (tree==finalDel && node.getInternalBody().contains(tree)){
                location =  node.getInternalBody().indexOf(tree);
            }
            node.getInternalBody().remove(tree);
            node.getChildren().remove(tree);
            if (location!=-1){
                for (stmt stmt : rhs.getInternalBody()) {
                    node.getInternalBody().add(location,stmt);
                    location+=1;
                }
            }
        }
        return super.visitIf (node);
    }

    @Override
    public Object visitWith(With node) throws Exception {
        for (PythonTree tree : del) {
            int location = -1;
            if (tree==finalDel && node.getInternalBody().contains(tree)){
                location =  node.getInternalBody().indexOf(tree);
            }
            node.getInternalBody().remove(tree);
            node.getChildren().remove(tree);
            if (location!=-1){
                for (stmt stmt : rhs.getInternalBody()) {
                    node.getInternalBody().add(location,stmt);
                    location+=1;
                }
            }
        }
        return super.visitWith (node);
    }

    @Override
    public Object visitAsyncWith(AsyncWith node) throws Exception {
        for (PythonTree tree : del) {
            int location = -1;
            if (tree==finalDel && node.getInternalBody().contains(tree)){
                location =  node.getInternalBody().indexOf(tree);
            }
            node.getInternalBody().remove(tree);
            node.getChildren().remove(tree);
            if (location!=-1){
                for (stmt stmt : rhs.getInternalBody()) {
                    node.getInternalBody().add(location,stmt);
                    location+=1;
                }
            }
        }
        return super.visitAsyncWith (node);
    }

    @Override
    public Object visitRaise(Raise node) throws Exception {


        return super.visitRaise (node);
    }

    @Override
    public Object visitTryExcept(TryExcept node) throws Exception {
        for (PythonTree tree : del) {
            int location = -1;
            if (tree==finalDel && node.getInternalBody().contains(tree)){
                location =  node.getInternalBody().indexOf(tree);
            }
            node.getInternalBody().remove(tree);
            node.getChildren().remove(tree);
            if (location!=-1){
                for (stmt stmt : rhs.getInternalBody()) {
                    node.getInternalBody().add(location,stmt);
                    location+=1;
                }
            }
        }
        return super.visitTryExcept (node);
    }

    @Override
    public Object visitTryFinally(TryFinally node) throws Exception {
        for (PythonTree tree : del) {
            int location = -1;
            if (tree==finalDel && node.getInternalBody().contains(tree)){
                location =  node.getInternalBody().indexOf(tree);
            }
            node.getInternalBody().remove(tree);
            node.getChildren().remove(tree);
            if (location!=-1){
                for (stmt stmt : rhs.getInternalBody()) {
                    node.getInternalBody().add(location,stmt);
                    location+=1;
                }
            }
        }
        return super.visitTryFinally (node);
    }

    @Override
    public Object visitAssert(Assert node) throws Exception {

        return super.visitAssert (node);
    }

    @Override
    public Object visitImport(Import node) throws Exception {

        return super.visitImport (node);
    }

    @Override
    public Object visitImportFrom(ImportFrom node) throws Exception {


        return super.visitImportFrom (node);
    }

    @Override
    public Object visitGlobal(Global node) throws Exception {


        return super. visitGlobal(node);
    }

    @Override
    public Object visitNonlocal(Nonlocal node) throws Exception {


        return super.visitNonlocal (node);
    }


    @Override
    public Object visitPass(Pass node) throws Exception {

        return super.visitPass (node);
    }

    @Override
    public Object visitBreak(Break node) throws Exception {

        return super.visitBreak (node);
    }

    @Override
    public Object visitContinue(Continue node) throws Exception {

        return super.visitContinue (node);
    }

    @Override
    public Object visitBoolOp(BoolOp node) throws Exception {



        return super.visitBoolOp (node);
    }

    @Override
    public Object visitBinOp(BinOp node) throws Exception {


        return super.visitBinOp (node);
    }

    @Override
    public Object visitUnaryOp(UnaryOp node) throws Exception {

        return super.visitUnaryOp (node);
    }

    @Override
    public Object visitLambda(Lambda node) throws Exception {


        return super.visitLambda (node);
    }

    @Override
    public Object visitIfExp(IfExp node) throws Exception {

        return super. visitIfExp(node);
    }

    @Override
    public Object visitDict(Dict node) throws Exception {

        return super.visitDict (node);
    }

    @Override
    public Object visitSet(Set node) throws Exception {

        return super.visitSet (node);
    }

    @Override
    public Object visitListComp(ListComp node) throws Exception {

        return super.visitListComp (node);
    }

    @Override
    public Object visitSetComp(SetComp node) throws Exception {

        return super.visitSetComp (node);
    }

    @Override
    public Object visitDictComp(DictComp node) throws Exception {

        return super.visitDictComp (node);
    }


    @Override
    public Object visitGeneratorExp(GeneratorExp node) throws Exception {

        return super.visitGeneratorExp (node);
    }

    @Override
    public Object visitAwait(Await node) throws Exception {

        return super.visitAwait (node);
    }

    @Override
    public Object visitYield(Yield node) throws Exception {


        return super.visitYield (node);
    }

    @Override
    public Object visitYieldFrom(YieldFrom node) throws Exception {

        return super.visitYieldFrom (node);
    }

    @Override
    public Object visitCompare(Compare node) throws Exception {

        return super.visitCompare (node);
    }

    @Override
    public Object visitCall(Call node) throws Exception {

        return super.visitCall (node);
    }

    @Override
    public Object visitNum(Num node) throws Exception {

        return super.visitNum (node);
    }

    @Override
    public Object visitHole(Hole node) throws Exception {

        return super.visitHole (node);
    }

    @Override
    public Object visitStr(Str node) throws Exception {

        return super.visitStr (node);
    }

    @Override
    public Object visitFormattedValue(FormattedValue node) throws Exception {

        return super.visitFormattedValue (node);
    }

    @Override
    public Object visitJoinedStr(JoinedStr node) throws Exception {

        return super.visitJoinedStr (node);
    }

    @Override
    public Object visitBytes(Bytes node) throws Exception {

        return super.visitBytes (node);
    }

    @Override
    public Object visitNameConstant(NameConstant node) throws Exception {

        return super.visitNameConstant (node);
    }

    @Override
    public Object visitEllipsis(Ellipsis node) throws Exception {

        return super.visitEllipsis (node);
    }

    @Override
    public Object visitConstant(Constant node) throws Exception {

        return super.visitConstant (node);
    }

    @Override
    public Object visitAttribute(Attribute node) throws Exception {

        return super.visitAttribute (node);
    }

    @Override
    public Object visitSubscript(Subscript node) throws Exception {

        return super.visitSubscript (node);
    }

    @Override
    public Object visitStarred(Starred node) throws Exception {


        return super.visitStarred (node);
    }

    @Override
    public Object visitName(Name node) throws Exception {

        return super.visitName (node);
    }

    @Override
    public Object visitList(List node) throws Exception {




        return super.visitList (node);
    }

    @Override
    public Object visitTuple(Tuple node) throws Exception {




        return super.visitTuple (node);
    }

    @Override
    public Object visitSlice(Slice node) throws Exception {

        return super.visitSlice (node);
    }

    @Override
    public Object visitExtSlice(ExtSlice node) throws Exception {

        return super.visitExtSlice (node);
    }

    @Override
    public Object visitIndex(Index node) throws Exception {

        return super.visitIndex (node);
    }

    @Override
    public Object visitExceptHandler(ExceptHandler node) throws Exception {

        return super.visitExceptHandler (node);
    }
}
