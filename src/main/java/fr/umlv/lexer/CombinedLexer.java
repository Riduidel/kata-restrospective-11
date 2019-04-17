package fr.umlv.lexer;

import java.util.Optional;
import java.util.function.Function;

public class CombinedLexer<Type> implements Lexer<Type> {

	private Lexer<Type> second;
	private Lexer<Type> first;

	public CombinedLexer(Lexer<Type> first, Lexer<Type> second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public Optional<Type> tryParse(String string) {
		var firstResult = first.tryParse(string);
		var secondResult = second.tryParse(string);
		return firstResult.or(() -> secondResult);
	}

	@Override
	public <Returned> Lexer<Returned> map(Function<String, Returned> mapper) {
		throw new UnsupportedOperationException();
	}

}
