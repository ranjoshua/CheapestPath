package server_side;

public interface SearchableGenerator<T, S> {
	Searchable generate(T convertToSearchable, S startPoint, S goalPoint);
}
