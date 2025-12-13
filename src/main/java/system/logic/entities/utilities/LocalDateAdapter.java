package system.logic.entities.utilities;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Converts LocalDate to String (for XML) and String back to LocalDate (for Java object)
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    // JAXB uses this method to convert a String from the XML file to a LocalDate object
    @Override
    public LocalDate unmarshal(String v) throws Exception {
        // Assuming the date in the XML is in the standard ISO-8601 format (e.g., "2025-12-17")
        return LocalDate.parse(v, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    // JAXB uses this method to convert a LocalDate object to a String for the XML file
    @Override
    public String marshal(LocalDate v) throws Exception {
        if (v == null) {
            return null;
        }
        // Format the LocalDate object into a standard string representation
        return v.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}