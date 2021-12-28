package Prism.util;

import Prism.core.*;

public class DynamicArray implements java.io.Serializable 
{
	public Object[] headList;
	public Object[] tailList;
	public Object[] iterator;
	public Object[] staticHead;        

	public int head;
	public int tail;
	public int iteratorIndex;
	public int size;
	public int segmentLength=10;

	public DynamicArray(int initialLength)
	{                
		if(initialLength < 2)
			initialLength = 10;
                segmentLength = initialLength;            
		headList = new Object[initialLength];      
	 	tailList = iterator = staticHead = headList;
		head = iteratorIndex = size = 0;
		tail = -1;
	}
	
	public synchronized void add(Object o)
	{
                tail++;
		if(tail > segmentLength - 2)
		{
			tailList[tail] = (Object) (new Object[segmentLength]);
			tailList = (Object []) tailList[tail];
			tail = 0;
		}

		tailList[tail] = o;                                
		size++;
		notify();
	}

	public synchronized String toString()
	{
		String list = new String();
		int i = head;
		Object[] iterator = headList;

		while (iterator[i] instanceof Object)
		{
			if(i == segmentLength - 1)
			{
				iterator = (Object[]) iterator[i];		
				i = 0;
			}
                        if (iterator[i] != null)
                        {
                            list += (iterator[i].toString()) + ((Brick)iterator[i]).name + ",\n";                            
                        }
                        i++;
		}
		return list;
	}

	public synchronized Object getNext()      // touch and proceed NO DELETION -- To be used when elements of the list are NEVER deleted
	{
//		System.out.println("Iterator index is " + iteratorIndex);
//		System.out.println("Segment Length is " + segmentLength);
		if(iteratorIndex == segmentLength - 1)
		{
			if(iterator[iteratorIndex] instanceof Object)
			{                               
				iterator = (Object []) iterator[iteratorIndex];			
				iteratorIndex = 0;                                
			}
		}

		Object o =iterator[iteratorIndex];
		iteratorIndex++;
		return(o);
	}

	public synchronized void reset()          // to be used in conjunction with getNext() to set the iterator to the beginning
	{
		iterator = staticHead;  
		iteratorIndex = 0;       
	}

	public synchronized boolean hasNext()     // to be used with getNext() and reset()
	{
		if(iterator[iteratorIndex] != null)
			return true;
		else
			return false;
	}

	public synchronized void remove(Object o)
	{                
		Object element;                
		this.reset();  
//		System.out.println("Size is " + size);
		while(this.hasNext())
		{
			element = this.getNext();
                        if(element.equals(o))
                        {
 					if(iterator[iteratorIndex-1] != tailList[tail])
					{                                                
						iterator[iteratorIndex-1] = tailList[tail];
						tailList[tail] = null;
					}
					else
					{
						//System.out.println("Element was last element");                                               
						iterator[iteratorIndex-1] = null;                                                
					}
					if(tail == 0)
                                        {                                                						
                                                int numLists = 1;
                                                if (size-1 > 0)
                                                {
                                                    numLists = (size-1)/(segmentLength-1);                                                
                                                    tail = segmentLength - 2;  
                                                }
                                                else
                                                    tail = -1;
                                                tailList = staticHead;
                                                for (int i=1; i< numLists; i++)
                                                {
                                                    tailList = (Object []) tailList[segmentLength-1];
                                                }
                                        }
					else                                                
                                            tail--;
                                            
					size--;
					break;         
			}
		}
	}
       
	public synchronized void remove(int index)
	{
            if (index < size)
            {
		Object element;
		int i = 0;
		this.reset();
		while(i<=index)
		{
			element = this.getNext();
			i++;
		}
                                
                if(iterator[iteratorIndex-1] != tailList[tail])
                {                                                
                        iterator[iteratorIndex-1] = tailList[tail];
                        tailList[tail] = null;                        
                }
                else
                {
                        //System.out.println("Element was last element");                                               
                        iterator[iteratorIndex-1] = null;                                                
                }
                if(tail == 0)
                {       
                        int numLists = 1;
                        if (size-1 > 0)
                        {
                            numLists = (size-1)/(segmentLength-1);                                                
                            tail = segmentLength - 2;  
                        }
                        else
                            tail = -1;
                        tailList = staticHead;
                        for (int j=1; j< numLists; j++)
                        {
                            tailList = (Object []) tailList[segmentLength-1];
                        }
                                                
                }
                else
                        tail--;                                        

                size--;   
            }
	}
        
	public synchronized Object get(int index)
	{
		Object element = null;
		int i = 0;
		this.reset();
		while(i<=index)
		{
			element = this.getNext();
			i++;
		}
		return(element);
	}
        
	public synchronized int indexOf(Object o)
	{
		Object element;
		this.reset();
		int j = 0;
		if (o instanceof String)
			o = (String) o;
		while(this.hasNext())
		{
			element = this.getNext();
//			System.out.println("Element is " + element.toString());
			if(element instanceof String)
			{
//				System.out.println("Got here");
				element = (String) element;
				if(element.equals(o))
				{
//					System.out.println("Returning index " + j + " corres " + element);
					return j;
				}
			}
			else if(element instanceof Brick && o instanceof Brick) 
			{
				if(((Brick)element).name.equals(((Brick)o).name))            // will not work 
				{
//					System.out.println("Returning index " + j + " corres " + ((Brick)element).name);
					return j;
				}
			}
			j++;
		}
		return -1;
	}

	public synchronized int size()
	{
		return(size);
	}

}

