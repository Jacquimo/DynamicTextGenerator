import java.util.*;


public class TestTraining {
	public static boolean trainingSuccess(StringArrayMap actual, String filename) {
		StringArrayMap expected = new StringArrayMap();
		ExampleTrainer.trainPrefixMap(expected, filename);
		
		Iterator ex = expected.getKeysIterator();
		Iterator act = actual.getKeysIterator();
		
		while (ex.hasNext()) {
			if (!act.hasNext())
				return false;
			
			Prefix e = expected.getPrefix((String[]) ex.next());
			Prefix a = actual.getPrefix((String[]) act.next());
			
			if (!e.equals(a)) // check to see that the prefix strings are the same
				return false;
			
			if (e.getNumSuffixes() != a.getNumSuffixes())
				return false;
			
		}
		if (act.hasNext()) // if this case were true, then the actual number of keys differs from the expected number of keys
			return false;
		
		return true;
	}
}
