import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Graph extends JFrame{
	
	public Graph(int nNodes,int nBranches,int[] startNodes,int[] endNodes,int[] gains,int tf) {
		JFrame frame = new JFrame("Test");
		frame.add(new JComponent() {
		    
		    {
		        setPreferredSize(new Dimension(600, 600));
		        repaint();
		    }

		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        g.drawString(""+tf, 400, 500);
		        for(int i=1;i<=nNodes;i++) {
		  
		        	 g.fillRect(100*i, 100,3,3); g.drawString(""+i, 100*i, 115);
		        }
		        
		        
		        for(int j=1;j<=nBranches;j++) {
		        	int distance=endNodes[j]-startNodes[j];
		        	
		        	if(distance==1 ) {
		        		g.drawLine(100*startNodes[j], 100, 100*endNodes[j],100);
		        		int arrow=100*endNodes[j];
		        		g.fillPolygon(new int[] {arrow,arrow-10,arrow-10},new int[] {100,95,105}, 3);
		        		g.drawString(""+gains[j],100*startNodes[j]+40 , 100);
		        	}else if(distance>1){
		        		g.drawArc(100*startNodes[j], 50, distance*100,40*distance, 0, 180);
		        		int arrow=100*endNodes[j];
		        		g.fillPolygon(new int[] {arrow,arrow,arrow-5},new int[] {100,90,95}, 3);
		        		g.drawString(""+gains[j],100*startNodes[j]+50*distance , 45);
		        	}else if(distance<-1) {
		        		g.drawArc(100*endNodes[j],(nNodes+distance-2)*(100/nNodes), -distance*100,-50*distance, 0, -180);
		        		int arrow=100*endNodes[j];
		        		g.fillPolygon(new int[] {arrow,arrow,arrow-5},new int[] {100,90,95}, 3);
		        		g.drawString(""+gains[j],100*endNodes[j]-50*distance ,100 -40*distance);
		        	}
		        	else if(distance==-1) {
		        		g.drawArc(100*endNodes[j], 80, 100, 40, 0, -180);
		        		int arrow=100*endNodes[j];
		        		g.fillPolygon(new int[] {arrow,arrow,arrow-5},new int[] {100,90,95}, 3);
		        		g.drawString(""+gains[j],100*endNodes[j]-50*distance ,140);
		        	}
		        }

		    }
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

		
	}
	public static void main(String[] args) {
		
	}
	
	

}


