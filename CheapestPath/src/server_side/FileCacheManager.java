package server_side;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FileCacheManager implements CacheManager {
	Map<String, String> myMap; // problem = key, solution = value
	String myFile = "HashMap.txt";
	static int numOfNewPairs;

	public class AppendingObjectOutputStream extends ObjectOutputStream {

		public AppendingObjectOutputStream(OutputStream out) throws IOException {
			super(out);
		}

		@Override
		protected void writeStreamHeader() throws IOException {
			// do not write a header, but reset:
			reset();
		}
	}

	public FileCacheManager() {
		myMap = new HashMap<>();
		numOfNewPairs = 0;
		if (!Files.exists(Paths.get(myFile))) // Checks whether there is a file with a problems&solutions to load into
												// my HashMap
			System.out.println("There's no Cache File to load from disc");
		else {
			try {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(myFile));
				try {
					myMap.putAll(((HashMap<String, String>) in.readObject()));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} finally {
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Cache File has been loaded from disc into HashMap");
			System.out.println("HashMap Size: " + myMap.size());
		}
	}

	public void addCache(String problem, String solution) {
		myMap.put(problem, solution);
		numOfNewPairs++;
		if (numOfNewPairs > 4)
			writeMapToDisc();
	}

	public boolean cacheExist(String problem) {
		return myMap.containsKey(problem);
	}

	public String getCache(String problem) {
		return myMap.get(problem);
	}

	public void writeMapToDisc() { // This method write the entire map to txt file on disc.
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(myFile));
			out.writeObject(myMap);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("HashMap has written to disc");
	}
}
