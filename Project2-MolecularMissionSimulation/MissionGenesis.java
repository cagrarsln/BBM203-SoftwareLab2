// Class representing the mission of Genesis
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.List;
public class MissionGenesis {

    // Private fields
    private MolecularData molecularDataHuman; // Molecular data for humans
    private MolecularData molecularDataVitales; // Molecular data for Vitales

    // Getter for human molecular data
    public MolecularData getMolecularDataHuman() {
        return molecularDataHuman;
    }

    // Getter for Vitales molecular data
    public MolecularData getMolecularDataVitales() {
        return molecularDataVitales;
    }

    // Method to read XML data from the specified filename
    // This method should populate molecularDataHuman and molecularDataVitales fields once called
    public void readXML(String filename) {
        try {
            File xmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            Element molecularDataElement = doc.getDocumentElement();

            NodeList humanMolecularDataNodes = molecularDataElement.getElementsByTagName("HumanMolecularData");
            if (humanMolecularDataNodes.getLength() > 0) {
                Node humanMolecularDataNode = humanMolecularDataNodes.item(0);
                if (humanMolecularDataNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element humanDataElement = (Element) humanMolecularDataNode;
                    molecularDataHuman = parseMolecularData(humanDataElement);
                }
            }

            NodeList vitalesMolecularDataNodes = molecularDataElement.getElementsByTagName("VitalesMolecularData");
            if (vitalesMolecularDataNodes.getLength() > 0) {
                Node vitalesMolecularDataNode = vitalesMolecularDataNodes.item(0);
                if (vitalesMolecularDataNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element vitalesDataElement = (Element) vitalesMolecularDataNode;
                    molecularDataVitales = parseMolecularData(vitalesDataElement);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MolecularData parseMolecularData(Element dataElement) {
        List<Molecule> molecules = new ArrayList<>();
        NodeList moleculeNodes = dataElement.getElementsByTagName("Molecule");

        for (int i = 0; i < moleculeNodes.getLength(); i++) {
            Node moleculeNode = moleculeNodes.item(i);
            if (moleculeNode.getNodeType() == Node.ELEMENT_NODE) {
                Element moleculeElement = (Element) moleculeNode;

                String id = moleculeElement.getElementsByTagName("ID").item(0).getTextContent();

                int bondStrength = Integer.parseInt(moleculeElement.getElementsByTagName("BondStrength").item(0).getTextContent());

                List<String> bonds = new ArrayList<>();
                NodeList bondNodes = moleculeElement.getElementsByTagName("MoleculeID");

                for (int j = 0; j < bondNodes.getLength(); j++) {
                    String bondID = bondNodes.item(j).getTextContent();
                    bonds.add(bondID);
                }

                Molecule molecule = new Molecule(id, bondStrength, bonds);

                molecules.add(molecule);

            }
        }

        return new MolecularData(molecules);
    }


}
