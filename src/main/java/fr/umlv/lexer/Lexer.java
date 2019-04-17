package fr.umlv.lexer;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

// TODO
public interface Lexer<Type> {
	public static <Type> Lexer<Type> create() {
		return NonWorking.<Type>create();
	}

	public Optional<Type> tryParse(String string);
	
	public static <Type> Lexer<Type> from(Pattern pattern) {
		Objects.requireNonNull(pattern, "pattern is null, and it shouldn't be null");
		return new PatternLexer(pattern);
	}
	public static <Type> Lexer<Type> from(String text) {
		return from(Pattern.compile(text));
	}
}