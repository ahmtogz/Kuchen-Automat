import java.io.*;
import java.util.ArrayList;
import java.util.Collection;


public class JOS {
    public static void serialize(String filename, Automat a){
        try (ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream(filename))){
            serialize(objectOutputStream, a);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void serialize(ObjectOutput objectOutput, Automat a) throws IOException {
        objectOutput.writeObject(a);
    }

    public static Automat deserialize(String filename){
        try (ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream(filename))){
            return deserialize(objectInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Automat deserialize(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        return (Automat)objectInput.readObject();
    }

    public boolean save(Automat automat){
        serialize("itemsJos", automat);
        return true;
    }

    public Automat load(){
        Automat automat= deserialize("itemsJos");
        return automat;
    }

}
