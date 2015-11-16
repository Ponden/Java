   public static void main(String argv[]) {
        String infile = DATA_FILE;
        String label = "name";
        
        if ( argv.length > 1 ) {
            infile = argv[0];
            label = argv[1];
        }
        
        UILib.setPlatformLookAndFeel();
        
        JFrame frame = new JFrame("p r e f u s e  |  r a d i a l g r a p h v i e w");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(demo(infile, label));
        frame.pack();
        
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        
        JMenuItem open = new JMenuItem("Open");
        JMenuItem exit = new JMenuItem("Exit");
        
        menu.add(open);
        menu.add(exit);
        
        frame.setVisible(true);
        
        open.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                JFileChooser openfile = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("XML File" , "xml");
                openfile.addChoosableFileFilter(filter);
                openfile.setCurrentDirectory(new File("/Users/Ponden/git/Prefuse/data"));
                openfile.setSelectedFile(new File("/Users/Ponden/git/Prefuse/data/socialnet.xml"));
                openfile.setFileSelectionMode(JFileChooser.FILES_ONLY);
                
                int ret = openfile.showDialog(null, "Open file");

                if (ret == JFileChooser.APPROVE_OPTION) 
                {
                  File file = openfile.getSelectedFile();
                  frame.setContentPane(demo(file.getAbsolutePath(), "name"));
                  frame.revalidate();  
                }
            }
        }); 
        
        exit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                System.exit(0);
            }
        });
        
    }
    
    public static JPanel demo() {
        return demo(DATA_FILE, "name");
    }
    
    public static JPanel demo(String datafile, final String label) {
        Graph g = null;
        try {
            g = new GraphMLReader().readGraph(datafile);
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(1);
        }
        return demo(g, label);
    }
    
    public static JPanel demo(Graph g, final String label) {        
        // create a new radial tree view
        final RadialGraphView gview = new RadialGraphView(g, label);
        Visualization vis = gview.getVisualization();
        
        // create a search panel for the tree map
        SearchQueryBinding sq = new SearchQueryBinding(
             (Table)vis.getGroup(treeNodes), label,
             (SearchTupleSet)vis.getGroup(Visualization.SEARCH_ITEMS));
        JSearchPanel search = sq.createSearchPanel();
        search.setShowResultCount(true);
        search.setBorder(BorderFactory.createEmptyBorder(5,5,4,0));
        search.setFont(FontLib.getFont("Tahoma", Font.PLAIN, 11));
        
        final JFastLabel title = new JFastLabel("                 ");
        title.setPreferredSize(new Dimension(350, 20));
        title.setVerticalAlignment(SwingConstants.BOTTOM);
        title.setBorder(BorderFactory.createEmptyBorder(3,0,0,0));
        title.setFont(FontLib.getFont("Tahoma", Font.PLAIN, 16));
        
        gview.addControlListener(new ControlAdapter() {
            public void itemEntered(VisualItem item, MouseEvent e) {
                if ( item.canGetString(label) )
                    title.setText(item.getString(label));
            }
            public void itemExited(VisualItem item, MouseEvent e) {
                title.setText(null);
            }
        });
        
        Box box = new Box(BoxLayout.X_AXIS);
        box.add(Box.createHorizontalStrut(10));
        box.add(title);
        box.add(Box.createHorizontalGlue());
        box.add(search);
        box.add(Box.createHorizontalStrut(3));
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gview, BorderLayout.CENTER);
        panel.add(box, BorderLayout.SOUTH);
        
        Color BACKGROUND = Color.WHITE;
        Color FOREGROUND = Color.DARK_GRAY;
        UILib.setColor(panel, BACKGROUND, FOREGROUND);
        
        return panel;
    }
