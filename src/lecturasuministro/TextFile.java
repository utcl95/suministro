
package lecturasuministro;


import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

public class TextFile {
    static String m_url="";
    static FileConnection ptr_file = null;

    TextFile(String url){
        m_url = url;
    }
    
    private void openUrl() {
    try {
            ptr_file = (FileConnection) Connector.open(m_url, Connector.READ);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void closeAll() {
        try {
            ptr_file.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public int numLineas() {
        InputStream is = null;
        int num = 0;
        openUrl();
        try {
            is = ptr_file.openInputStream();
            int ch;
            //StringBuffer sb = new StringBuffer();
            while((ch=is.read()) != -1) {
                //sb.append(ch);
                //System.out.print(sb.toString());
                //System.out.println(ch);
                if (ch == '\n')
                    num++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        closeAll();
        is = null;
        return (num+1);
    }

    public String readLine(int line) {
        InputStream is_rl = null;
        String sLine = "";
        int num = 0; int ch;
        openUrl();
        try {
            is_rl = ptr_file.openInputStream();
            ch = is_rl.read();
            while (ch != -1) {
                if (ch == '\n') {
                    num++;
                }
                
                if(line == (num+1)) {
                    sLine = rLine(is_rl, ch, line);
                    break;
                }
                ch = is_rl.read();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        closeAll();
        is_rl = null;
        return sLine;
    }

    private String rLine(InputStream is, int fc, int line) {
        StringBuffer sb = new StringBuffer();
        int ch;        
        try {
            //ch = is.read();
            while ((ch=is.read()) != '\n') {
                if(line==1) {
                    sb.append((char)fc);
                    line++;
                }
                if(ch == -1)
                    break;
                sb.append((char)ch);
                
                //ch = is.read();
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }


    public String[] split(String original) {
        Vector nodes = new Vector();
        String separator = "|";
        System.out.println("split start...................");
        // Parse nodes into vector
        int index = original.indexOf(separator);
        while(index>=0) {
            nodes.addElement( original.substring(0, index) );
            original = original.substring(index+separator.length());
            index = original.indexOf(separator);
        }
        // Get the last node
        nodes.addElement( original );

        // Create splitted string array
        String[] result = new String[ nodes.size() ];
        if( nodes.size()>0 ) {
            for(int loop=0; loop<nodes.size(); loop++) {
                result[loop] = (String)nodes.elementAt(loop);
                System.out.println(result[loop]);
            }
        }
        return result;
    }

}
