package storage;

public interface IDynamicArray<T> {
	int size();
	void add(T item);
	T get(int index);
	void set(T item, int index);
	void add(T item, int index);
	T remove(int index);
	
	
}
