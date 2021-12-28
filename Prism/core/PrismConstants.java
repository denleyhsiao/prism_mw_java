package Prism.core;

/**
 * Constants used in the Prism.
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public interface PrismConstants {
	public static final int REQUEST = 1; // same as TOP in C2 style

	public static final int REPLY = 2; // same as BOTTOM in C2 style

	public static final int REQUEST_REPLY = 3; // Hybrid of REQUEST/REPLY

	// ============> Here are style constants:
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

	public static final int SUBSCRIPTION_REQ = 133;

	public static final int UNSUBSCRIPTION_REQ = 134;

	public static final int PUB_SUB_EVENT_NAME = 135;

	public static final int P2P_COMP = 140;

	public static final int P2P_ARCH = 141;

	public static final int UNICAST_CONN = 150;

	public static final int UNICAST_DST_BRICK = 151;

	public static final int UNICAST_DST_IP = 152;

	// ===========> Here are Meta-Level Comamnds for supporting the component
	// deployment
	public static final int CMD_WELD_PORT = 300; // Weld Mutual Ports Command

	public static final int CMD_UNWELD_PORT = 301; // Unweld Mutual Ports
													// Command

	public static final int CMD_INSTAN_COMP = 302; // Instantiate Component
													// Command

	public static final int CMD_INSTAN_CONN = 303; // Instantiate Connector
													// Command

	public static final int CMD_INSTAN_PORT = 304; // Instantiate Port Command

	public static final int CMD_SEND_COMP = 305; // Send Component Command

	public static final int CMD_RECEIVE_COMP = 306; // Receive Component Command

	public static final int CMD_UNLOAD_COMP = 307; // Unload Component Command

	public static final int CMD_ARCH_START = 308; // Architecture Start
													// Command

	public static final int CMD_EXTPORT_CONNECT = 309; // Extensible Port
														// Connect Command

	public static final int CMD_REG_SVC = 310; // Register Service Command

	public static final int CMD_BRICK_START = 311; // Brick Start Command

	public static final int CMD_SET_BOARD_TYPE = 312; // Set Board Type
														// Command

	public static final int CMD_RECEIVE_FILE = 313;

	public static final int CMD_UPGRADE_COMPONENT = 314; // Upgrade Component
															// Command

	public static final int CMD_CHANGE_TO_IDLE = 315; // Change To Idle
														// Command

	public static final int CMD_REMOVE_ROUTING_INFO = 316; // Remove Routing
															// Info Command

	public static final int CMD_REMOVE_COMPONENT = 317; // Remove Component
														// Command

	public static final int CMD_REMOVE_CONNECTOR = 318; // Remove Connector
														// Command

	public static final int DEPLOYMENT_REQ = 350;

	public static final int DEPLOYMENT_ACK = 351;

	public static final int CONNECTION_FROM_ADMIN = 352;

	public static final int UPDATE_ACK = 353;

	// Payload names for the component deployment
	public static final int DLL_FILE = 400;

	public static final int DEST_IP_ADDR = 401;

	public static final int COMP_FILE_NAME = 402;

	public static final int COMP_NAME = 403;

	public static final int CONN_NAME = 404;

	public static final int CREATE_BASIC_REQUEST_PORT = 405;

	public static final int CREATE_BASIC_REPLY_PORT = 406;

	public static final int CREATE_DIST_CLIENT_PORT = 407;

	public static final int CREATE_DIST_SERVER_PORT = 408;

	public static final int SERVER_ADDR = 409;

	public static final int SERVER_PORT_NUM = 410;

	public static final int PARENT_COMP = 411;

	public static final int PARENT_CONN = 412;

	public static final int REQ_PORT_NAME = 413;

	public static final int REP_PORT_NAME = 414;

	public static final int EXT_PORT_NAME = 415;

	public static final int STYLE = 416;

	public static final int GLOBAL = 417;

	public static final int LSD_REQ_PORT = 418;

	public static final int GSD_IP = 419;

	public static final int GSD_PORT = 420;

	public static final int BOARD_TYPE = 421;

	public static final int BACKUP = 422;

	public static final int FT_TYPE = 423;

	public static final int PORT_PROTOCOL = 424;

	public static final int LSD_REP_PORT = 425;

	public static final int COMMAND_NUM = 426;

	public static final int DEPENDENT_COMPONENTS = 427;

	public static final int COMP_CURRENT_VERSION = 428;

	public static final int COMP_NEW_VERSION = 429;

	public static final int IS_DLL = 430;

	public static final int PATH = 480;

	// ===========> Payload names for the multicast-based event exchanges
	// / IP address of a remote address
	public static final int IP_ADDRESS = 500;

	// / process ID
	public static final int PROCESS_ID = 501;
	
	public static final boolean DEBUG_MODE = true;

}
