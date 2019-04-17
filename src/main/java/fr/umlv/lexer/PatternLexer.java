package fr.umlv.lexer;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;

public class PatternLexer implements Lexer<String> {

	private Pattern pattern;

	public PatternLexer(Pattern pattern) {
		this.pattern = pattern;
	}

	@Override
	public Optional<String> tryParse(String string) {
		Objects.requireNonNull(string, "string to parse must not be null");
		var matcher = pattern.matcher(string);
		if(matcher.matches()) {
			return Optional.ofNullable(matcher.group(1));
		} else {
			return Optional.empty();
		}
	}
}
