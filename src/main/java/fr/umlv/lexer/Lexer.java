package fr.umlv.lexer;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
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

	public <Returned> Lexer<Returned> map(Function<? super String, Returned> mapper);
	
	public default Lexer<Type> or(Lexer<Type> other) {
		Objects.requireNonNull(other);
		return new CombinedLexer(this, other);
	}
	
	public default Lexer<Type> with(Pattern pattern, Function<? super String, Type> mapper) {
		Objects.requireNonNull(pattern, "pattern should not be null");
		Objects.requireNonNull(mapper, "mapper should not be null");
		return or(from(pattern).map(mapper));
	}
	
	public default Lexer<Type> with(String string, Function<? super String, Type> mapper) {
		Objects.requireNonNull(string, "pattern should not be null");
		Objects.requireNonNull(mapper, "mapper should not be null");
		return or(from(string).map(mapper));
	}
	
	public static <Type> Lexer<Type> from(Collection<String> patterns, Collection<Function<? super String, Type>> mappers) {
		Objects.requireNonNull(patterns, "patterns should not be null");
		Objects.requireNonNull(mappers, "mappers should not be null");
		if(patterns.size()!=mappers.size())
			throw new IllegalArgumentException(String.format("both lists should be of the same size. patterns contains %d elements but mappers %d", patterns.size(), mappers.size()));
		var first = patterns.iterator();
		var second = mappers.iterator();
		Lexer<Type> returned = create();
		while(first.hasNext() && second.hasNext()) {
			returned = returned.with(first.next(), second.next());
		}
		return returned;
	}
}