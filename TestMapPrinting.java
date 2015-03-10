public class TestMapPrinting {
	public static void main(String[] args) {
		StringArrayMap map = new StringArrayMap();
		PrefixGenerator.trainPrefixMap(map, "small.txt");
		map.printMap();
	}
}
