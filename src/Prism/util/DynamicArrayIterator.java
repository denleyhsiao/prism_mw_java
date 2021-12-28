package Prism.util;
import Prism.core.*;
                  
public class DynamicArrayIterator extends Object
{
	public DynamicArray array;
	public int iteratorIndex;
	public Object[] iterator;
	public DynamicArrayIterator(DynamicArray init)
	{
		array=init;
	}
	
	public synchronized void reset()          // to be used in conjunction with getNext() to set the iterator to the beginning
	{
		iterator = array.staticHead;  
		iteratorIndex = 0;   
		
	}
	public synchronized Object getNext()      // touch and proceed NO DELETION -- To be used when elements of the list are NEVER deleted
	{
		if(iteratorIndex == array.segmentLength - 1)
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
	public synchronized boolean hasNext()     // to be used with getNext() and reset()
	{
		if(iterator[iteratorIndex] != null)
			return true;
		else
			return false;
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
			if(element instanceof String)
			{
				element = (String) element;
				if(element.equals(o))
				{
					return j;
				}
			}
			else if(element instanceof Brick && o instanceof Brick) 
			{
				if(((Brick)element).name.equals(((Brick)o).name))         
				{
					return j;
				}
			}
			j++;
		}
		return -1;
	}
}