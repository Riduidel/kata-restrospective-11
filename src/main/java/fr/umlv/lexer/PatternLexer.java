package fr.umlv.lexer;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class PatternLexer<Type> implements Lexer<Type> {

	private Pattern pattern;

	public PatternLexer(Pattern pattern) {
		this.pattern = pattern;
	}

	@Override
	public Optional<Type> tryParse(String string) {
		Objects.requireNonNull(string, "text on which pattern is tested is null, and it shouldn't be null");
		var matcher = pattern.matcher(string);
		if(matcher.matches()) {
			String group = matcher.group(1);
			return Optional.of((Type) group); 
		} else {
			return Optional.empty();
		}
	}

}
