package fr.umlv.lexer;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public interface Lexer<Type> {
	static final Pattern REGEXP_DETECTOR = Pattern.compile(".*\\(^\\)\\).*");
	
	static Lexer<String> noop = new NoopLexer();

	static <Type> Lexer<Type> create() {
		return (Lexer<Type>) noop;
	}

	static Lexer<String> from(Pattern pattern) {
		Objects.requireNonNull(pattern, "pattern must not be null");
		analyzePattern(pattern.pattern());
		return new PatternLexer(pattern);
	}

	static Lexer<String> from(String pattern) {
		Objects.requireNonNull(pattern, "pattern must not be null");
		return from(Pattern.compile(pattern));
	}

	static <Type> Lexer<Type> from(List<String> patterns, List<? extends Function<? super String, ? extends Object>> mappers) {
		return fromP(patterns.stream()
							.map(Pattern::compile)
							.collect(Collectors.toList())
				, mappers);
	}
	static <Type> Lexer<Type> fromP(List<Pattern> patterns, List<? extends Function<? super String, ? extends Object>> mappers) {
		Objects.requireNonNull(patterns);
		Objects.requireNonNull(mappers);
		if(patterns.size()!=mappers.size())
			throw new IllegalArgumentException(
					String.format("can't have different sizes in patterns (%d, args) and matchers (%d)",
							patterns.size(),
							mappers.size())
					);
		var pat = patterns.iterator();
		var map = mappers.iterator();
		Lexer<Type> returned = create();
		while(pat.hasNext()&&map.hasNext()) {
			returned = (Lexer<Type>) returned.with(pat.next(), map.next());
		}
		return returned;
	}

	static void analyzePattern(String pattern) {
		var good = true;
		var first = pattern.indexOf('(');
		if (first < 0) {
			good = false;
		} else {
			good = pattern.indexOf('(', first + 1) < 0;
			if (good) {
				var last = pattern.indexOf(')');
				if (last > 0) {
					good = pattern.indexOf(')', last + 1) < 0;
				} else {
					good = false;
				}
			}
		}
		if (!good)
			throw new IllegalArgumentException(
					String.format("Can't use pattern %s - it doesn't have the right number of groups", pattern));
	}

	Optional<Type> tryParse(String string);
	
	public default <Transformed> Lexer<Transformed> map(Function<? super Type, Transformed> mapper) {
		Objects.requireNonNull(mapper, "mapper can't be null!");
		return new MapperLexer<Type, Transformed>(this, mapper);
	}

	public default Lexer<Object> or(Lexer<?> other) {
		Objects.requireNonNull(other, "Other lexer can't be null");
		return new OrLexer(this, other);
	}
	
	public default Lexer<Object> with(String pattern, Function<? super String, ? extends Object> mapper) {
		Objects.requireNonNull(pattern);
		return with(Pattern.compile(pattern), mapper);
	}
	public default Lexer<Object> with(Pattern pattern, Function<? super String, ? extends Object> mapper) {
		Objects.requireNonNull(pattern);
		Objects.requireNonNull(mapper);
		return new OrLexer(this, from(pattern).map(mapper));
	}
}
