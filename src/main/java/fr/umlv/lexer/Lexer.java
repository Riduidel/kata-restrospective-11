package fr.umlv.lexer;

import java.util.Optional;

// TODO
public interface Lexer<Type> {
	public static <Type> Lexer<Type> create() {
		return NonWorking.<Type>create();
	}

	public Optional<Type> tryParse(String string);
}