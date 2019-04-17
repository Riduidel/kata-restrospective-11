package fr.umlv.lexer;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;

public class PatternLexer<Type> implements Lexer<Type> {

	private Pattern pattern;
	private Function<? super String, Type> mapper;

	public PatternLexer(Pattern pattern) {
		this(pattern, value -> (Type) value);
	}

	public PatternLexer(Pattern pattern, Function<? super String, Type> mapper) {
		this.pattern = pattern;
		this.mapper = mapper;
	}

	@Override
	public Optional<Type> tryParse(String string) {
		Objects.requireNonNull(string, "text on which pattern is tested is null, and it shouldn't be null");
		var matcher = pattern.matcher(string);
		if(matcher.matches()) {
			String group = matcher.group(1);
			return Optional.ofNullable(mapper.apply(group)); 
		} else {
			return Optional.empty();
		}
	}

	@Override
	public <Returned> Lexer<Returned> map(Function<? super String, Returned> mapper) {
		Objects.requireNonNull(mapper, "Frankly speaking, using a null mapper is just dumbass, no ?");
		return new PatternLexer<Returned>(pattern, mapper);
	}

}
