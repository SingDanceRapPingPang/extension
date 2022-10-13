package au.com.d2dcrc.yago2es;

/**
 * Created by Zaiwen FENG on 26/06/2017.
 * Modificated by pillar on 22/11/2020
 */

import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.parser.NxParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**Each object of this class represents an entites**/
public class KarmaFact {

    //private String id;
    private String subject;
    private String predicate;
    private String object;


    /**Default Constructor of Yago Fact*
     * @param subject subject of the fact
     * @param predicate predict of the fact
     * @param object object of the fact
     * */
    public KarmaFact (String subject, String predicate, String object){

        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
    }

    public KarmaFact() {

    }


    public void setSubject (String subject) {

        this.subject = subject;
    }

    public String getSubject() {

        return this.subject;
    }

    public void setPredicate (String predicate) {

        this.predicate = predicate;
    }

    public String getPredicate() {

        return this.predicate;
    }

    public void setObject (String object) {

        this.object = object;
    }

    public String getObject() {

        return this.object;
    }
    public static List<KarmaFact> parseTriples(String file_path){

        List<KarmaFact> karmaFacts = new ArrayList<KarmaFact>();

        List<KarmaFact> filteredTriples = new ArrayList<KarmaFact>();

        try{

            FileInputStream is = new FileInputStream(file_path);

            NxParser nxp = new NxParser();

            nxp.parse(is);

            while(nxp.hasNext()){

                /**get a triple**/
                Node[] nx = nxp.next();
                String subject = nx[0].toString();
                String predicate = nx[1].toString();
                String object = nx[2].toString();

                /**Create subject, predicate, and object of Predicate**/
                Subject subject1 = new Subject(subject);
                Predicate predicate1 = new Predicate(predicate);
                ObjectOfPredicate object1 = new ObjectOfPredicate(object);

                /**create a triple **/
                KarmaFact karmaFact = new KarmaFact();
                karmaFact.setSubject(subject1.toString());
                karmaFact.setPredicate(predicate1.toString());
                karmaFact.setObject(object1.toString());

                karmaFacts.add(karmaFact);
            }

            /**filter the triples of which the predicate are '#type'**/
            filteredTriples = filterPredicate(karmaFacts, new String("type"));


        }catch (IOException e){

            e.printStackTrace();
        }

        return filteredTriples;
    }

    /**Filter the triple in which the predicate contains a special string 'str', from a triple array list named 'triples'*
     * @param triples a list of triples
     * @param str string that must be filtered
     * @return filtered list of triples
     * */
    public static List<KarmaFact> filterPredicate(List<KarmaFact> triples, String str){

        List<KarmaFact> filteredTriples = new ArrayList<KarmaFact>();

        /**loop 'triples'**/
        Iterator<KarmaFact> it = triples.iterator();
        while (it.hasNext()){

            KarmaFact karmaFact = it.next();

            if(!karmaFact.getPredicate().contains(str)){

                /**add the triple to 'filteredTriples'**/
                filteredTriples.add(karmaFact);
            }


        }

        return filteredTriples;
    }

}


