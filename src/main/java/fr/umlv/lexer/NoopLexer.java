package fr.umlv.lexer;

import java.util.Objects;
import java.util.Optional;

public class NoopLexer implements Lexer<String> {

	@Override
	public Optional<String> tryParse(String string) {
		Objects.requireNonNull(string);
		return Optional.empty();
	}

}
