import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListArray<T> implements List<T> {
	/*
	 * This is an inner class built for organizing the operations for each node in
	 * this list array
	 */
	public class Node<T> {
		T data[];
		private int arraySize;
		private int nextIndex;

		// This is the constructor
		@SuppressWarnings("unchecked")
		public Node() {
			arraySize = 5;
			nextIndex = 0;
			data = (T[]) new Object[arraySize];
		}

		@SuppressWarnings("unchecked")
		public Node(int size) {
			arraySize = size;
			nextIndex = 0;
			data = (T[]) new Object[arraySize];
		}

		/*
		 * This method helps us get a certain element in the node by taking the index of
		 * the element as input
		 */
		public T get(int index) {
			return data[index];
		}

		/*
		 * This method helps us set a certain element in the node by taking the index
		 * and the element to be assigned as input.
		 */
		public T set(int index, T element) {
			T originalValue = data[index];
			data[index] = element;
			return originalValue;
		}

		/*
		 * This method prints the visual view of each node.
		 */
		public String toString() {
			String nodeView = nextIndex + "[";
			for (int i = 0; i < data.length; i++) {
				if (i < arraySize - 1) {
					nodeView += data[i] + ",";
				} else
					nodeView += data[i] + "";
			}
			nodeView += "]";
			return nodeView;
		}

		/*
		 * This method adds an element to the end of the node
		 */
		public void append(T value) {
			data[nextIndex] = value;
			nextIndex++;
		}

		// This method checks if the node is full.
		public boolean isFull() {
			return nextIndex == arraySize;
		}

		// This method checks if the node is empty.
		public boolean isEmpty() {
			return nextIndex == 0;
		}

		/*
		 * This methods inserts an element in the node
		 */
		public T add(int index, T element) {
			if (index >= nextIndex) {
				throw new ArrayIndexOutOfBoundsException();
			}
			T lastElement = data[data.length - 1];
			for (int i = data.length - 1; i > index; i--) {
				data[i] = data[i - 1];

			}
			data[index] = element;
			if (nextIndex != data.length) {
				nextIndex++;
			}
			return lastElement;
		}

		/*
		 * This method removes an element from the node.
		 */
		public T remove(int index) {
			T removedValue = data[index];
			for (int i = index; i < data.length - 1; i++) {
				data[i] = data[i + 1];
			}
			data[data.length - 1] = null;
			nextIndex--;
			return removedValue;
		}

		// This method returns the position of the element in the node.
		public int indexOf(Object o) {
			for (int i = 0; i < data.length; i++) {
				if (data[i] != null && data[i].equals(o)) {
					return i;
				}
			}
			return -1;
		}

		// Checks if a certain element has been removed.
		public boolean remove(T val) {
			int index = indexOf(val);
			if (index != -1) {
				remove(index);
				return true;
			} else
				return false;
		}

		// Checks if a certain element(taken as input) is present in the node.
		public boolean contains(Object o) {
			for (int i = 0; i < data.length; i++) {
				if (data[i] != null && data[i].equals(o)) {
					return true;
				}
			}
			return false;
		}

		// Returns the number of elements.
		public int size() {
			return nextIndex;
		}

		public Node<T> next;
	}

	private int nodeSize;
	private Node<T> head;

	/*
	 * The below three methods are the constructors for the list array. First one is
	 * default. Second one takes the size of the node as input. Third one takes the
	 * size of the node and list as input.
	 */
	public ListArray() {
		this.nodeSize = 5;
		head = new Node<T>(nodeSize);
	}

	public ListArray(int nodeSize) {
		this.nodeSize = nodeSize;
		head = new Node<T>(nodeSize);
	}

	public ListArray(int nodeSize, int listSize) {
		this.nodeSize = nodeSize;
		head = new Node<T>(nodeSize);
		int count = 0;
		if (listSize % nodeSize == 0) {
			count = listSize / nodeSize;
		} else {
			count = listSize / nodeSize + 1;
		}
		for (int i = 0; i < count; i++) {
			Node<T> node = new Node();
		}
	}

	// Returns the number of elements in this list array.
	@Override
	public int size() {
		int count = 0;
		Node<T> current = head;
		while (current != null) {
			count += current.size();
			current = current.next;
		}
		return count;
	}

	// Checks if the list array is empty or not.
	@Override
	public boolean isEmpty() {
		return head == null;
	}

	/*
	 * Checks if the list array contains a certain element.
	 */
	@Override
	public boolean contains(Object o) {
		Node<T> current = head;
		while (current != null) {
			if (current.contains(o)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	/*
	 * The below method and inner class implement the iterator method which helps us
	 * loop over items in a certain collection.
	 */
	@Override
	public Iterator<T> iterator() {
		return new ListArrayIterator<T>(this);
	}

	@SuppressWarnings("hiding")
	public class ListArrayIterator<T> implements Iterator<T> {
		private ListArray<T> sourceList;
		int currentIndex;

		public ListArrayIterator(ListArray<T> list) {
			sourceList = list;
			currentIndex = 0;
		}

		@Override
		public boolean hasNext() {
			return currentIndex < sourceList.size();
		}

		@Override
		public T next() {
			T value = sourceList.get(currentIndex);
			currentIndex++;
			return value;
		}

	}

	// Takes the elements of the list array and puts them in an array.
	@Override
	public Object[] toArray() {
		@SuppressWarnings("unchecked")
		T array[] = (T[]) new Object[size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = get(i);
		}
		return array;
	}

	@Override
	public <T1> T1[] toArray(T1[] a) {
		return null; // DO NOT IMPLEMENT THIS
	}

	// Checks if an element is added to the list array.
	@Override
	public boolean add(T e) {
		Node<T> current = head;
		while (current.next != null) {
			current = current.next;
		}
		if (current.isFull()) {
			Node<T> node = new Node<T>(nodeSize);
			current.next = node;
			node.append(e);
		} else {
			current.append(e);
		}
		return true;
	}

	// Checks if an element is removed in the list array.
	@Override
	public boolean remove(Object o) {
		if (indexOf(o) != -1) {
			remove(indexOf(o));
			return true;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return false; // DO NOT IMPLEMENT THIS
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return false; // DO NOT IMPLEMENT THIS
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return false; // DO NOT IMPLEMENT THIS
	}

	// Removes every element from the list.
	@Override
	public void clear() {
		head = null;

	}

	// This method helps us get a certain element by taking in its position as
	// input.
	@Override
	public T get(int index) {
		if (isEmpty()) {
			return null;
		}
		Node<T> current = head;
		int count = 0;
		while (current != null) {
			if (index / nodeSize == count) {

				return (T) current.get(index % nodeSize);
			}
			count++;
			current = current.next;
		}
		return null;
	}

	/*
	 * This method helps us set a certain element by taking in a desired position
	 * and the element as input.
	 */
	@Override
	public T set(int index, T element) {
		if (isEmpty()) {
			return null;
		}
		Node<T> current = head;
		int count = 0;
		while (current != null) {
			if (index / nodeSize == count) {
				return (T) current.set(index % nodeSize, element);

			}
			count++;
			current = current.next;
		}
		return null;
	}

	// Adds an element to the list array.
	@Override
	public void add(int index, T element) {
		Node<T> current = head;
		int count = 0;
		Node<T> insertNode = null;
		while (current != null) {
			if (index / nodeSize == count) {

				insertNode = current;
				break;
			}
			count++;
			current = current.next;
		}
		if (insertNode == null) {
			return;
		}
		T value = null;
		Node<T> temp = insertNode;
		boolean wasFull = false;
		while (temp != null) {
			if (temp.next == null) {
				wasFull = temp.isFull();
			}
			if (temp.equals(insertNode)) {
				value = temp.add(index % nodeSize, element);
			} else {
				value = temp.add(0, value);

			}
			temp = temp.next;
		}

		if (wasFull) {
			Node<T> lastNode = head;
			while (lastNode.next != null) {
				lastNode = lastNode.next;
			}
			Node<T> node = new Node<T>(nodeSize);
			lastNode.next = node;
			node.append(value);
		}
	}

	// Removes an element from the list array.
	@Override
	public T remove(int index) {
		Node<T> current = head;
		int count = 0;
		T value = null;
		T removedElement = null;
		Node<T> removeNode = null;
		while (current != null) {
			if (index / nodeSize == count) {

				removeNode = current;
				break;
			}
			count++;
			current = current.next;
		}

		Node<T> temp = removeNode;
		Node<T> prev = null;
		while (temp != null) {
			if (temp.equals(removeNode)) {
				removedElement = temp.remove(index % nodeSize);
			} else {
				value = temp.remove(0);
				prev.append(value);
			}
			prev = temp;
			temp = temp.next;
		}
		if (prev.isEmpty() && prev != head) {
			Node<T> check = head;
			while (check.next != prev) {
				check = check.next;
			}
			check.next = null;
		}
		return removedElement;

	}

	// Return the position of a certain element in the list array.
	@Override
	public int indexOf(Object o) {
		Node<T> current = head;
		int count = 0;
		while (current != null) {
			count++;
			for (int i = 0; i < current.data.length; i++) {
				if (current.indexOf(o) == i)
					return i + nodeSize * (count - 1);
			}
			current = current.next;
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		return 0;
	}

	@Override
	public ListIterator<T> listIterator() {
		return null; // DO NOT IMPLEMENT THIS
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return null; // DO NOT IMPLEMENT THIS
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return null;
	}

	@Override
	public void sort(Comparator<? super T> c) {
		// Use quicksort here
	}

	// Returns a visual view of the entire list array.
	public String toString() {
		Node<T> current = head;
		String listArray = "";
		while (current != null) {
			listArray += current.toString() + "->";
			current = current.next;
		}
		return listArray;
	}
}
