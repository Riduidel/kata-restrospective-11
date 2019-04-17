package fr.umlv.lexer;

import java.util.Optional;
import java.util.function.Function;

public class MapperLexer<InitialType, Transformed> implements Lexer<Transformed> {

	private Lexer<InitialType> parent;
	private Function<? super InitialType, Transformed> mapper;

	public MapperLexer(Lexer<InitialType> lexer, Function<? super InitialType, Transformed> mapper) {
		this.parent = lexer;
		this.mapper = mapper;
	}

	@Override
	public Optional<Transformed> tryParse(String string) {
		var parentResult = parent.tryParse(string);
		return parentResult.map(mapper::apply);
	}

}
