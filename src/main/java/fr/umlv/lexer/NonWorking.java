package fr.umlv.lexer;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class NonWorking<Type> implements Lexer<Type> {
	private static Lexer lexer;
	
	public static <Type> Lexer<Type> create() {
		if(lexer==null)
			lexer = new NonWorking<>();
		return lexer;
	}
	

	@Override
	public Optional<Type> tryParse(String text) {
		Objects.requireNonNull(text);
		return Optional.empty();
	}


	@Override
	public <Returned> Lexer<Returned> map(Function<String, Returned> mapper) {
		return (Lexer<Returned>) this;
	}
}