package fr.umlv.lexer;

import java.util.Objects;
import java.util.Optional;

// TODO
public class Lexer<Type> {
	
	private static Lexer lexer;
	
	public static <Type> Lexer<Type> create() {
		if(lexer==null)
			lexer = new Lexer<>();
		return lexer;
	}

	public Optional<Type> tryParse(String text) {
		Objects.requireNonNull(text);
		return Optional.empty();
	}
}