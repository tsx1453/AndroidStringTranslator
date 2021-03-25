## how to use
1. Create a java file with path config/Constants.java and write your api key as ```public static final String GOOGLE_API_KEY = $your_key```
2. Copy the text you need to translate to```resources/strings.xml```
3. Define the target language in ```Main.java - targetLanguages```
4. Crate a writer object for Translation object, LocalResourcesWriter will write to ```result-$target.xml``` in resource, ProjectResourcesWriter will write to the project's res dir