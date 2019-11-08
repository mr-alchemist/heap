
import storage.FactorArray;

public class Heap {
	public FactorArray<Integer> list = new FactorArray<Integer>(); 
	int heapSize = 0;
	public int getSize() {
		return heapSize;
	}
	public Heap() {
		
	}
	
	Integer getEl(int i) {
		if(i >= heapSize || i < 0)
			return null;
		return list.get(i);
	}
	
	void setEl(int i, Integer val) {
		list.set(val, i);
	}
	
	int parentIndex(int i){
		return (i - 1)/2;
	}
	
	int leftChildIndex(int i) {
		return 2*i + 1;
	}
	
	int rightChildIndex(int i) {
		return 2*i + 2;
	}
	
	void swap(int i1, int i2) {
		int buf = getEl(i1);
		setEl(i1, getEl(i2));
		setEl(i2, buf);
	}
	
	void siftUp(int i) {
		while(i > 0 && getEl(i) > getEl( parentIndex(i) ) ) {
			swap(i, parentIndex(i));
			i = parentIndex(i);
		}
	}
	
	void DrownRecursive(int i) {//рекурсивная версия Drown
		int indexOfMax = i;
		int lci = leftChildIndex(i);
		if(lci < heapSize && getEl(lci) > getEl(i))
			indexOfMax = lci;
		int rci = rightChildIndex(i);
		if(rci < heapSize && getEl(rci) > getEl(indexOfMax))
			indexOfMax = rci;
		if(indexOfMax == i)
			return;
		swap(i, indexOfMax);
		DrownRecursive(indexOfMax);
	}
	
	void Drown(int i) {//
		int indexOfMax = i;
		
		while(true) {
			int rci = rightChildIndex(i);
			int lci = leftChildIndex(i);
			if(lci < heapSize && getEl(lci) > getEl(i))
				indexOfMax = lci;
			
			if(rci < heapSize && getEl(rci) > getEl(indexOfMax))
				indexOfMax = rci;
			if(indexOfMax == i)
				return;
			swap(i, indexOfMax);
			
			i = indexOfMax;
		}
	}
	
	public int getMax() throws Exception{
		if(heapSize == 0)
			throw new Exception("Error: the heap is empty.");
		return getEl(0);
	}
	
	public void add(int p) {//добавление элемента в кучу с сохранением свойств кучи
		heapSize++;
		if(list.size() < heapSize) {
			list.add(p);
		}
		else {
			setEl(heapSize-1, p);
		}
		siftUp(heapSize-1);
	}
	
	public void  remove(int i) throws Exception {
		if(i >= heapSize)
			throw new Exception("Error of removing: index " + i + " is out of boundary, the heap size is " + heapSize);
		if(i < 0)
			throw new Exception("Error of removing: index " + i + " is out of boundary it should be 0(zero) at least");
		int overMax = getMax() + 1;
		setEl(i, overMax);
		siftUp(i);
		extractMax();
	}
	
	public int extractMax() throws Exception {
		if(heapSize == 0)
			throw new Exception("Error: the heap is empty.");
		int maxValue = getEl(0);
		setEl(0, getEl(heapSize-1));
		heapSize--;
		Drown(0);
		return maxValue;
	}
	
	public void changePriority(int i, int p) throws Exception {
		if(i >= heapSize)
			throw new Exception("Error: index " + i + " is out of boundary, the heap size is " + heapSize);
		if(i < 0)
			throw new Exception("Error: index " + i + " is out of boundary it should be 0(zero) at least");
		int oldP = getEl(i);
		setEl(i, p);
		
		if(p > oldP) 
			siftUp(i);
		
		if(p < oldP) 
			Drown(i);
	}
	
	void buildHeap() {
		int startFrom = (heapSize - 1) / 2;
		for(int i = startFrom; i >= 0; i--) {
			Drown(i);
		}
	}
	
	void heapSort() {
		buildHeap();
		for(int i=heapSize-1; i >= 1; i--) {
			swap(0, i);
			heapSize--;
			Drown(0);
			
		}
	}
	
	static String getNumStr(int n, int w) {
		String s = Integer.toString(n);
		int sLength = s.length();
		if(sLength < w) {
			int diff = w - sLength;
			if(diff%2 != 0) {
				s = s + " ";
				diff--;
			}
			for(int i=0;i < diff;i+=2) {
				s = " "+ s + " ";
			}
		}
		return s;
	}
	
	static String getNSpb(int cnt) {
		String s = "";
		for(int i = 0; i < cnt; i++)
			s += " ";
		return s;
	}
	
	static void printHeap(Heap heap) {
		int width = 6;
		int curLevel = 0;
		int elmCntr = 0;
		while(elmCntr < heap.getSize()) {
			int elmCntInLevel = (int)Math.pow(2, curLevel);
			elmCntr += elmCntInLevel;
			curLevel++;
		}
		int totalLevels = curLevel;
		System.out.println("-----");
		System.out.println("totalLevels: " + totalLevels);
		int elmsMaxCntInLastLevel = (int)Math.pow(2, totalLevels -1);
		int maxLettersCntInRow = elmsMaxCntInLastLevel*width;
		
		int index = 0;
		for(curLevel=0;curLevel < totalLevels;curLevel++) {
			int elmCntInLevel = (int)Math.pow(2, curLevel);
			String s = "";
			s += getNSpb( (maxLettersCntInRow - elmCntInLevel*width) / (2*elmCntInLevel) );
			for(int i=1; i < elmCntInLevel; i++) {
				if(index >= heap.getSize())
					break;
				int val = heap.list.get(index);
				s += getNumStr(val, width);
				index++;
				s += getNSpb( (maxLettersCntInRow - elmCntInLevel*width) / elmCntInLevel );
			}
			if(index < heap.getSize()) {
				int val2 = heap.list.get(index);
				s += getNumStr(val2, width);
				index++;
			}
			s += getNSpb( (maxLettersCntInRow - elmCntInLevel*width) / (2*elmCntInLevel) );
			System.out.println(s);
			System.out.println(getNSpb(maxLettersCntInRow));
		}
	}
	
	public static void main(String[] args) {
		System.out.println("start");
		Heap heap = new Heap();
		
		/*heap.add(12);
		heap.add(6);
		heap.add(5);
		heap.add(18);
		heap.add(7);
		heap.add(21);
		heap.add(11);
		heap.add(10);
		heap.add(15);
		heap.add(20);*/
		
		
		//добавляем в "массив" произвольные значения:
		heap.list.add(12);
		heap.list.add(6);
		heap.list.add(5);
		heap.list.add(18);
		heap.list.add(7);
		
		heap.list.add(21);
		heap.list.add(11);
		heap.list.add(10);
		heap.list.add(15);
		heap.list.add(20);
		heap.heapSize = heap.list.size();
		
		heap.heapSort();//сортируем
		
		//выводим значения:
		for(int i = 0; i < heap.list.size() ; i++) {
			System.out.println(heap.list.get(i));
		}
		
		
		//printHeap(heap);
		
	}
}
