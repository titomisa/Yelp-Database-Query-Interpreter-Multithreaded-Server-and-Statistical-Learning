package ca.ece.ubc.cpen221.mp5;
// Generated from Query.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class provides an empty implementation of {@link QueryListener},
 * which can be extended to create a listener which only needs to handle a subset
 * of the available methods.
 */

//http://www.geeksforgeeks.org/expression-tree/
public class QueryBaseListener implements QueryListener {
	private Set<YelpRestaurant> allRests;
	private Stack<Addable> stack;
	/**
	 * Constructor
	 */
	public QueryBaseListener(Map<String, Record> records){
		stack = new Stack<>();
		allRests = records.entrySet().parallelStream().map(t->t.getValue())
				.filter(t->t.getType().equals("business"))
				.map(t->(YelpRestaurant)t).collect(Collectors.toSet());
	}

	/**
	 * Enter a parse tree produced by {@link QueryParser#root}.
	 * @param ctx the parse tree
	 */
	public void enterRoot(QueryParser.RootContext ctx){
		//added
		stack.push(new Or());
	}

	/**
	 * Exit a parse tree produced by {@link QueryParser#root}.
	 * @param ctx the parse tree
	 */
	public void exitRoot(QueryParser.RootContext ctx){
		assert stack.size() == 1;
	}
	/**
	 * Enter a parse tree produced by {@link QueryParser#orExpr}.
	 * @param ctx the parse tree
	 */
	public void enterOrExpr(QueryParser.OrExprContext ctx){
		stack.push(new Or());
	}
	/**
	 * Exit a parse tree produced by {@link QueryParser#orExpr}.
	 * @param ctx the parse tree
	 */
	public void exitOrExpr(QueryParser.OrExprContext ctx){
		Or or = (Or) stack.pop();
		stack.peek().addOperation(or);
	}
	/**
	 * Enter a parse tree produced by {@link QueryParser#andExpr}.
	 * @param ctx the parse tree
	 */
	public void enterAndExpr(QueryParser.AndExprContext ctx){
		stack.push(new And());
	}
	/**
	 * Exit a parse tree produced by {@link QueryParser#andExpr}.
	 * @param ctx the parse tree
	 */
	public void exitAndExpr(QueryParser.AndExprContext ctx){
		And and = (And) stack.pop();
		stack.peek().addOperation(and);
	}
	/**
	 * Enter a parse tree produced by {@link QueryParser#atom}.
	 * @param ctx the parse tree
	 */
	public void enterAtom(QueryParser.AtomContext ctx){

	}
	/**
	 * Exit a parse tree produced by {@link QueryParser#atom}.
	 * @param ctx the parse tree
	 */
	public void exitAtom(QueryParser.AtomContext ctx){
	}
	/**
	 * Enter a parse tree produced by {@link QueryParser#in}.
	 * @param ctx the parse tree
	 */
	public void enterIn(QueryParser.InContext ctx){

	}
	/**
	 * Exit a parse tree produced by {@link QueryParser#in}.
	 * @param ctx the parse tree
	 */
	public void exitIn(QueryParser.InContext ctx){
		String tokenString = ctx.STRING().toString();

		stack.peek().addOperation(new In(tokenString));
	}
	/**
	 * Enter a parse tree produced by {@link QueryParser#category}.
	 * @param ctx the parse tree
	 */
	public void enterCategory(QueryParser.CategoryContext ctx){

	}
	/**
	 * Exit a parse tree produced by {@link QueryParser#category}.
	 * @param ctx the parse tree
	 */
	public void exitCategory(QueryParser.CategoryContext ctx){
		String tokenString = ctx.STRING().toString();
		stack.peek().addOperation(new Category(tokenString));
	}
	/**
	 * Enter a parse tree produced by {@link QueryParser#name}.
	 * @param ctx the parse tree
	 */
	public void enterName(QueryParser.NameContext ctx){
	}
	/**
	 * Exit a parse tree produced by {@link QueryParser#name}.
	 * @param ctx the parse tree
	 */
	public void exitName(QueryParser.NameContext ctx){
		String tokenString = ctx.STRING().toString();
		stack.peek().addOperation(new Name(tokenString));
	}
	/**
	 * Enter a parse tree produced by {@link QueryParser#rating}.
	 * @param ctx the parse tree
	 */
	public void enterRating(QueryParser.RatingContext ctx){

	}
	/**
	 * Exit a parse tree produced by {@link QueryParser#rating}.
	 * @param ctx the parse tree
	 */
	public void exitRating(QueryParser.RatingContext ctx){
		String ineq = ctx.ineq().getChild(0).getText();
		Double num = Double.valueOf(ctx.NUM().toString());
		stack.peek().addOperation(new Rating(ineq, num));
	}
	/**
	 * Enter a parse tree produced by {@link QueryParser#price}.
	 * @param ctx the parse tree
	 */
	public void enterPrice(QueryParser.PriceContext ctx){
	}
	/**
	 * Exit a parse tree produced by {@link QueryParser#price}.
	 * @param ctx the parse tree
	 */
	public void exitPrice(QueryParser.PriceContext ctx){
		String ineq = ctx.ineq().getChild(0).getText();
		Integer price = Integer.valueOf(ctx.NUM().toString());
		stack.peek().addOperation(new Price(ineq, price));
	}
	/**
	 * Enter a parse tree produced by {@link QueryParser#ineq}.
	 * @param ctx the parse tree
	 */
	public void enterIneq(QueryParser.IneqContext ctx){

	}
	/**
	 * Exit a parse tree produced by {@link QueryParser#ineq}.
	 * @param ctx the parse tree
	 */
	public void exitIneq(QueryParser.IneqContext ctx){

	}

	/**
	 * Returns a filtered list of restaurants satisfying the query conditions
	 */
	public Set<YelpRestaurant> evaluate(){
		return stack.get(0).evaluate(allRests);
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterEveryRule(ParserRuleContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitEveryRule(ParserRuleContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void visitTerminal(TerminalNode node) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void visitErrorNode(ErrorNode node) { }
}