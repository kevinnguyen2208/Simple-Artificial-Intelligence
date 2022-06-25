import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Main {

    static JFrame f;
    static JLabel l, l1, l2;
    static JComboBox c1, c2;
    public static boolean inBattle = false;

    public static void main(String[] args) throws Exception {
        f = new JFrame("Robot Navigation");
        f.setLayout(new FlowLayout());

        String files[] = { "map1.txt", "map2.txt", "map3.txt" };
        String algos[] = { "bfs", "dfs", "as", "gbfs", "cus1", "cus2"};

        c1 = new JComboBox(files);
        c2 = new JComboBox(algos);
        l = new JLabel("WELCOME");
        l1 = new JLabel("Select Map");
        l2 = new JLabel("Select Algorithm");
        l.setForeground(Color.blue);
        l1.setForeground(Color.blue);
        l2.setForeground(Color.blue);
        JButton b=new JButton("Run");

        f.add(l);
        f.add(l1);
        f.add(c1);
        f.add(l2);
        f.add(c2);
        f.add(b);

        f.setSize(400, 400);
        f.setMinimumSize(new Dimension(400, 400));
        f.setMaximumSize(new Dimension(400, 400));
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        l.setBounds(150,50,110,30);
        l1.setBounds(80,100,110,30);
        c1.setBounds(220,100,110,30);
        l2.setBounds(80,200,110,30);
        c2.setBounds(220,200,110,30);
        b.setBounds(145,300,110,30);

        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                inBattle=true;
            }
        });

        while(inBattle == false){
            try {
                Thread.sleep(200);
            } catch(InterruptedException e) {
            }
        }

        String filename = (String)c1.getSelectedItem();

        ResourceInitializer ri = new ResourceInitializer();
        ri.Initialize(filename);

        ri.getGrid();

        String strategy = (String)c2.getSelectedItem();

        System.out.println(filename);
        System.out.println(strategy);

        switch (strategy.toLowerCase()) {
            case "bfs":
                BFS.BFSSearch(ri.getGrid(), ri.getRows(), ri.getColumns(), ri.getStartPos(), ri.getEndPos());
                break;
            case "dfs":
                DFS dfs = new DFS(ri.getGrid(), ri.getRows(), ri.getColumns(), ri.getStartPos(), ri.getEndPos());
                dfs.DFSSearch();
                break;
            case "as":
                AS.as(ri.getGrid(), ri.getRows(), ri.getColumns(), ri.getStartPos(), ri.getEndPos());
                break;
            case "gbfs":
                GBFS.gbfs(ri.getGrid(), ri.getRows(), ri.getColumns(), ri.getStartPos(), ri.getEndPos());
                break;
            case "cus1":
                CUS1 cus = new CUS1(ri.getGrid(), ri.getRows(), ri.getColumns(), ri.getStartPos(), ri.getEndPos());
                cus.cusSearch();
                break;
            case "cus2":
                CUS2 cus2 = new CUS2(ri.getGrid(), ri.getRows(), ri.getColumns(), ri.getStartPos(), ri.getEndPos());
                cus2.cusSearch();
                break;
            default: {
                System.out.print("Please choose a valid algorithm! (bfs, dfs, as, gbfs, cus1, cus2)");
                System.exit(0);
            }
        }
    }
}