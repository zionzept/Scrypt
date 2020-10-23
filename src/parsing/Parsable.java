package parsing;

public interface Parsable {
	public void addToStringBuilder(StringBuilder sb, int nestledness);
	public void addToStringBuilderDetailed(StringBuilder sb, int nestledness);
}