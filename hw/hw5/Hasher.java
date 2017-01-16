public interface Hasher<E> {
	public int hash(E elem);
}

class ColorCodeHasher implements Hasher<Color> {
// so when you override hash(Color) it returns the value of the parameter
	@Override
	public int hash(Color elem) {
		return elem.getCode();
	}
}

class ColorNameHasher implements Hasher<Color> {
// so when you override hash(Color) it returns an int hashing the String of the parameter the same way as shown in Lesson 8, or your own algorithm
	@Override
	public int hash(Color elem) {
		//	 fix this one
		return elem.getName().hashCode();
	}
}
