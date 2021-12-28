package Prism.core;

/**
 * Constants used in the Prism.
 * 
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public interface PrismConstants
{
    public static final int REQUEST=1;    //same as TOP in C2 style
    public static final int REPLY=2;      //same as BOTTOM in C2 style
    public static final int REQUEST_REPLY=3;    //Hybrid of REQUEST/REPLY	
    
    //============> Here are style constants:
    public static final int DEFAULT = 100;
    
	public static final int C2_COMP = 101;
	public static final int C2_CONN = 102;
	public static final int C2_ARCH = 103;
	
	public static final int FILTER = 110;
    public static final int PIPE = 111;
    public static final int PIPE_FILTER_ARCH = 112;
    
    public static final int CLIENT = 120;
	public static final int SERVER = 121;
	public static final int CLIENT_SERVER_ARCH = 122;
	
    public static final int PUB_SUB_COMP = 130;
    public static final int PUB_SUB_CONN = 131;
    public static final int PUB_SUB_ARCH = 132;
    
    public static final int P2P_COMP = 140;
    public static final int P2P_ARCH = 141;
  
}
