package Prism.core;

import Prism.util.*;

/**
 * This interface is implemented by any Handler class in Prism. The Handler provides a mechanism for event distribution that complies
 * with a particular event distribution policy. A handler is assigned to a connector.
 * 
 *@version 2.0
 *@author USC Soft. Arch. Group. Contact: Sam Malek <A HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public interface IHandler 
{
        /**
         * The implementation of this method shall provide the distribution
         * policy and rules.
         * @param e     Incoming event to be distributed
         */
        public void handle(Event e);
	
        /**
         * The implementation shall allow the Handler object to set its
         * parent conector.
         *@param conn   parent connector of this Handler object
         */
	public void setParentConnector(IConnector conn);
}