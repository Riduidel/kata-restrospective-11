package fr.umlv.lexer;

import java.util.Optional;
import java.util.function.Function;

public class OrLexer implements Lexer<Object> {

	private Lexer<Object> first;
	private Lexer<Object> second;

	public OrLexer(Lexer patternLexer, Lexer other) {
		this.first = patternLexer;
		this.second = other;
	}

	@Override
	public Optional<Object> tryParse(String string) {
		return first.tryParse(string).or(() -> second.tryParse(string));
	}
}