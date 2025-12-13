package system.data;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Singleton class responsible for persisting (storing and loading) the
 * application's data structure (Data object) to and from an XML file
 * using JAXB.
 */
public class XmlPersister {

    // --- 1. Singleton Instance and Accessor ---
    private static XmlPersister theInstance;
    private final String path;

    /**
     * Provides the single, globally accessible instance of the XmlPersister.
     * Initializes it to use "data.xml" if it hasn't been created yet.
     * @return The singleton instance of XmlPersister.
     */
    public static XmlPersister instance() {
        if (theInstance == null) {
            theInstance = new XmlPersister("data.xml");
        }
        return theInstance;
    }

    // Private constructor to enforce the Singleton pattern
    private XmlPersister(String p) {
        path = p;
    }

    // --- 2. Data Loading (Unmarshalling) ---

    /**
     * Loads the application data from the configured XML file.
     * @return The Data object populated from the XML file.
     * @throws Exception if an I/O or JAXB error occurs.
     */
    public Data load() throws Exception {
        // 1. Create JAXBContext, which is expensive, and should be created once per application type.
        JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);

        // 2. Create the Unmarshaller
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        // 3. Use try-with-resources for automatic closing of the stream
        try (FileInputStream is = new FileInputStream(path)) {
            // 4. Perform unmarshalling (read XML and convert to Java object)
            Data result = (Data) unmarshaller.unmarshal(is);
            return result;
        } catch (IOException e) {
            // Re-throw a generic exception or a custom persistence exception
            throw new Exception("Error reading XML file: " + e.getMessage(), e);
        }
    }

    // --- 3. Data Storing (Marshalling) ---

    /**
     * Stores the current Data object to the configured XML file.
     * @param d The Data object to persist.
     * @throws Exception if an I/O or JAXB error occurs.
     */
    public void store(Data d) throws Exception {
        // 1. Create JAXBContext
        JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);

        // 2. Create the Marshaller
        Marshaller marshaller = jaxbContext.createMarshaller();

        // Optional: Make the output XML formatted and readable
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // 3. Use try-with-resources for automatic closing of the stream
        try (FileOutputStream os = new FileOutputStream(path)) {
            // 4. Perform marshalling (write Java object to XML)
            marshaller.marshal(d, os);
            os.flush();
        } catch (IOException e) {
            // Re-throw a generic exception or a custom persistence exception
            throw new Exception("Error writing to XML file: " + e.getMessage(), e);
        }
    }
}