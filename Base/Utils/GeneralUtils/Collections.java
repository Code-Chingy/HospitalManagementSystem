package com.techupstudio.Base.Utils.GeneralUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.techupstudio.Base.Utils.GeneralUtils.Funcs.*;


public class Collections {

    private Collections(){}

    public static class EmptyObjectException extends Exception{
        EmptyObjectException(String object){ println( "\n"+object+" is Empty.");}
    }

    public static class KeyValuePair<K,V>{
        private K KEY;
        private V VALUE;

        KeyValuePair(){ }

        public KeyValuePair(K key, V value){ setKey(key);setValue(value);}

        public void setKey(K key){ KEY = key; }

        public void setValue(V value){ VALUE = value; }

        public K getKey(){ return KEY; }

        public V getValue(){ return VALUE; }

        public String toString(){ return format("KeyValuePair[<> : <>]", getKey(), getValue()); }

    }

    public static class Enumerator<T>{

        private int index = 0;
        private String TYPE;

        private T[] ARRAY;
        private List<T> LIST;
        private Collection<T> COLLECTION;
        private LinkedList<T> LINKEDLIST;
        private MasterList<T> MASTERLIST;

        Enumerator(T[] list){ ARRAY = list; TYPE = "array"; }
        Enumerator(List<T> list){ LIST = list; TYPE = "list"; }
        Enumerator(Collection<T> list){ COLLECTION = list; TYPE = "collection"; }
        Enumerator(LinkedList<T> list){ LINKEDLIST = list; TYPE = "linkedlist"; }
        Enumerator(MasterList<T> list){ MASTERLIST = list; TYPE = "masterlist"; }



        public boolean hasNext(){
            switch (TYPE){
                case "array":
                    return ( ARRAY.length > index);
                case "list":
                    return ( LIST.size() > index);
                case "collection":
                    return ( COLLECTION.size() > index);
                case "linkedlist":
                    return ( LINKEDLIST.size() > index);
                case "masterlist":
                    return ( MASTERLIST.size() > index);
            }
            return false;
        }

        public T getNext(){
            if (hasNext()) {
                index++;
                switch (TYPE) {
                    case "array":
                        return ARRAY[index - 1];
                    case "list":
                        return LIST.get(index - 1);
                    case "collection":
                        List<T> l = (List<T>) COLLECTION;
                        return l.get(index - 1);
                    case "linkedlist":
                        return LINKEDLIST.get(index - 1);
                    case "masterlist":
                        return MASTERLIST.pop(index - 1);
                }
            }
            return null;
        }

        public int size(){
            switch (TYPE){
                case "array":
                    return ARRAY.length;
                case "list":
                    return LIST.size();
                case "collection":
                    return COLLECTION.size();
                case "linkedlist":
                    return LINKEDLIST.size();
                case "masterlist":
                    return MASTERLIST.size();
            }
            return -1;
        }

        public void reset(){ index = 0; }

        public int enumerationsRemaining(){ return size() - index; }

        public int enumerationsPassed(){ return index ; }

        public boolean isEmpty(){ return size() == 0; }

    }

    public static class MasterList<T> implements Iterable<T> {

        private List<T> DATA = new ArrayList<>();

        public MasterList(){}

        MasterList(T[] obj){ DATA = changeToList(obj); }

        MasterList(List<T> obj){ DATA = obj; }

        MasterList(MasterList<T> obj){ DATA = changeToList(obj); }

        private List<T> changeToList(T[] obj) {
            List<T> n = new ArrayList<>(); for (T i : obj) { n.add(i); } return n;
        }

        private List<T> changeToList(MasterList<T> obj){
            List<T> n = new ArrayList<>();
            for (int i : range(obj.size())){
                n.add((T) obj.pop(i));
            }
            return n;
        }

        public MasterList join(List<T> obj){ DATA.addAll(obj); return clone(); }

        public MasterList join(MasterList<T> obj){
            for (T i : obj.toArray()){ DATA.add(i); } return clone(); }

        public MasterList append(T... obj){ for (T i : obj){ DATA.add(i); } return clone(); }

        public MasterList appendleft(T obj){ DATA.add(0,obj); return clone(); }

        public T pop(int index){ return DATA.get(index); }

        public T popfirst(){ if (!isEmpty()) return DATA.get(0); return null;}

        public T poplast(){  if (!isEmpty()) return DATA.get(size()-1); return null; }

        public void assign(List<T> obj){ DATA = obj; }

        public void assign(MasterList<T> obj){ DATA = changeToList(obj); }

        public void assign(T[] obj){ DATA = changeToList(obj); }

        public MasterList insert(int index, T obj){ DATA.add(index, obj); return clone(); }

        public MasterList update(int index, T obj){ DATA.set(index, obj); return clone(); }

        public MasterList updateAll(T old_obj, T new_obj){
            while (DATA.contains(old_obj)){
                update(lastIndexOf(old_obj), new_obj);
            }
            return clone();
        }

        public MasterList remove (int index){ DATA.remove(index); return clone(); }

        public MasterList removeAll (T obj){
            while (DATA.contains(obj)){
                remove(lastIndexOf(obj));
            }
            return clone();
        }

        public void clear (){ DATA.clear(); }

        public int count(T obj){
            List<T> REMDATA = new ArrayList<>();
            REMDATA.addAll(DATA);int count = 0;
            while (REMDATA.contains(obj)){
                count++;
                REMDATA.remove(REMDATA.lastIndexOf(obj));
            }
            return count;
        }

        public MasterList<T> reverse(){
            MasterList<T> n = new MasterList<>();
            for (int i : range(size())){
                n.append(pop(size()-(i+1)));
            }
            return n;
        }

        public MasterList<T> sample(int size){ return sample(0,size); }

        public MasterList<T> sample(int begin_index, int end_index){
            MasterList<T> n = new MasterList<>();
            for (int i : range(begin_index,end_index)){
                n.append(pop(i));
            }
            return n;
        }

        public MasterList<T> randsample(int size){
            return new MasterList(Funcs.randsample(DATA.toArray(), size)); }

        public int firstIndexOf(T obj){ return DATA.indexOf(obj); }

        public int lastIndexOf(T obj){ return DATA.lastIndexOf(obj); }

        public String toString(){ return DATA.toString(); }

        public int hashCode(){ return DATA.hashCode(); }

        public MasterList<T> clone(){ return new MasterList(DATA);  }

        public void forEach (Consumer<? super T> action){ DATA.forEach(action); }

        public Iterator<T> iterator() { return DATA.iterator(); }
        public ListIterator<T> listiterator() { return DATA.listIterator(); }
        public Iterator<T> listiterator(int index) { return DATA.listIterator(index); }

        public boolean equal (Object obj){
            if (obj.getClass().getSimpleName().contains("List")){
                if (obj.toString().equals(toString())){
                    return true;
                }
            }
            return false;
        }

        public boolean equal (MasterList<T> obj){ return DATA.toArray().equals(obj.toArray()); }

        public int size (){ return DATA.size(); }

        public boolean contains (T... obj){
            if (obj.length > 1){
                for (T i : obj){
                    if (!contains(i)){
                        return false;
                    }
                }
                return true;
            }
            return DATA.contains(obj[0]);
        }

        public boolean contains (MasterList<T> obj){
            for (T i : obj.toArray()){
                if (!contains(i)){
                    return false;
                }
            }
            return true;
        }

        public boolean contains (Collection<?> c){ return DATA.containsAll(c); }

        public boolean isEmpty (){ return DATA.isEmpty(); }

        public T[] toArray (){ return (T[]) DATA.toArray(); }

        public T[] toArray (T[] array){ return DATA.toArray(array); }

        public List<T> toList (){ return DATA; }

        public T[] iter (){ return toArray(); }

        public MasterList<T> toSet (){ return new MasterList(asSet()); }

        public List asSet(){
            List<T> n = new ArrayList<>();
            DATA.forEach((o) -> {
                if (!n.contains(o)) {
                    n.add(o);
                }
            });
            return n;
        }

        public MasterList<T> sort() { return changeToMyList(new SortedNumArray(toDouble(DATA.toArray())).toDouble()); }

        private MasterList<T> changeToMyList(Double[] toDouble) {
            MasterList<T> n = new MasterList<>();for (Double i : toDouble){ n.append((T) i); }return n;
        }

        public boolean issupersetOf (List<T> obj){ return DATA.containsAll(obj); }

        public boolean issubsetOf (List<T> obj){ return obj.containsAll(DATA); }

        public boolean issupersetOf (MasterList<T> obj){
            List<T> n = new ArrayList<>();
            for (T i : obj.toArray()){
                n.add(i);
            }
            return DATA.containsAll(n);
        }

        public boolean issubsetOf (MasterList<T> obj){
            List<T> n = new ArrayList<>();
            for (T i : obj.toArray()){
                n.add(i);
            }
            return n.containsAll(DATA);
        }

        public Enumerator<T> getEnumerator(){ return new Enumerator<>(this); }

    }

    public static class Dictionary<K,V> implements Iterable<KeyValuePair<K,V>> {

        private Map<K,V> DATA = new HashMap<>();

        public Dictionary(){}
        public Dictionary(Map<K,V> obj){ DATA = obj;}

        public void assign(Map<K,V> obj){ DATA = obj; }
        public void join(Map<K,V> obj){ obj.forEach((k,v)-> DATA.put(k,v)); }
        public void add(K key, V value){ DATA.put(key,value); }
        public V get(K key){ return DATA.get(key); }
        public void addIfAbsent(K key, V value){ DATA.putIfAbsent(key,value); }
        public V getOrDefault(K key, V defaultValue){ return DATA.getOrDefault(key, defaultValue); }
        public void remove(K key){ DATA.remove(key); }
        public void remove(K key, V value){ DATA.remove(key,value); }
        public void replace(K key, V value){ DATA.replace(key,value); }
        public void replace(K key, V value, V new_value){ DATA.replace(key,new_value); }
        public boolean hasKey(K key){ return DATA.containsKey(key); }
        public boolean hasValue(K value){ return DATA.containsValue(value); }
        public boolean hasKeyValue(K key, V value){ return DATA.containsKey(key) && get(key) == value; }
        public Set<K> keys(){ return DATA.keySet(); }
        public Collection<V> values(){ return DATA.values(); }

        public int count(K key) {
            int count = 0;
            for (K i : DATA.keySet()){
                if (key == i){
                    count++;
                }
            }
            return count;
        }
        public int count(K key, V value) {
            int count = 0;
            for (K i : DATA.keySet()){
                if (key == i && value == DATA.get(key)){
                    count++;
                }
            }
            return count;
        }

        public String[][] iter(){
            String[][] n = new String[size()][];
            int index = 0;
            for (K i : DATA.keySet()){
                n[index] = new String[]{i.toString(), get(i).toString()};
                index++;
            }
            return n;
        }


        public int size()  { return DATA.size(); }
        public void clear() { DATA.clear(); }
        public void forEach(BiConsumer<? super K, ? super V> action) { DATA.forEach(action); }
        public Dictionary clone() { return new Dictionary(DATA); }
        public String toString() { return DATA.toString(); }
        public int hashCode() { return DATA.hashCode(); }
        public boolean isEmpty() { return DATA.isEmpty(); }

        private List<KeyValuePair<K,V>> getList(){
            List<KeyValuePair<K,V>> list = new ArrayList<>();
            forEach((k, v) -> list.add(new KeyValuePair<>(k,v)));
            return list;
        }

        public Enumerator<KeyValuePair<K,V>> getEnumerator(){ return new Enumerator<>(getList()); }

        @Override
        public Iterator<KeyValuePair<K,V>> iterator() { return getList().iterator(); }
    }

    public static class StatsArray extends MasterList {

        public StatsArray() {}

        StatsArray(double... numbers){ assign(numbers); }

        StatsArray(List<Double> numbers){ assign(numbers);}

        StatsArray(MasterList<Double> numbers){ assign(numbers); }

        public StatsArray(Double[] numbers) { assign(numbers);}

        public void assign(double... numbers) { super.clear();for (Double i : numbers){ append(i); } }

        private Double[] getSorted(){ return new SortedNumArray(toDouble(super.toArray())).toDouble(); }
        public Object[] getSortedArray(){ return getSorted(); }
        public Object[] getSet(){ return super.asSet().toArray(); }
        public Object[] getSortedSet(){ return new StatsArray(getSorted()).asSet().toArray(); }

        public Object getLCM (){ return Funcs.getLCM(toDouble(super.asSet().toArray())); }
        public Object getHCF (){ return Funcs.getHCF(toDouble(super.asSet().toArray())); }
        public Object[] getShuffle (){ return shuffle(toDouble(toArray())); }
        public Object[] getSample (int end_index){ return super.sample(end_index).toArray();}
        public Object[] getSample (int start_index, int end_index){ return super.sample(start_index, end_index).toArray();}
        public Object[] getRandomSample (int size){ return super.randsample(size).toArray(); }
        public Object[][] getRandomSample (int size, int groups){ return Funcs.randsample(toDouble(toArray()), size, groups); }


        public double getMean(){ return  (double) getSum()/super.size(); }

        public double getMax(){ return getSorted()[size()-1]; }

        public double getMin(){ return getSorted()[0]; }

        public double getSum(){
            double total = 0.0;
            for (int i : range(super.size())) { total += (double) super.pop(i); }
            return total;
        }

        public double getMedian(){
            double mMedian = 0.0;
            if (size() > 1) {
                if (size() % 2 == 0) { mMedian = (getSorted()[(size()/2)-1] + getSorted()[(size()/2)])/2; }
                else { mMedian = getSorted()[((size()-1) / 2)]; }
            }
            else{ if (size() == 1){ mMedian =  (double) super.pop(0); } }
            return mMedian;
        }

        public String getMode(){
            int maxCount = 0;
            boolean duplicateMode = false;
            Object mMode = 0.0;
            if (size() > 1) {
                for (int i : range(super.toSet().size())) {
                    int counter = count(super.toSet().pop(i));
                    if (counter == maxCount){
                        duplicateMode = true;
                    }
                    if (counter > maxCount) {
                        duplicateMode = false;
                        maxCount = counter;
                        mMode = pop(i);
                    }
                }
                if (duplicateMode){
                    return null;
                }
                return mMode.toString();
            }
            else{
                if (size() == 1){
                    mMode = pop(0);
                }
            }
            return  mMode.toString();
        }

        public String getModeCount(){
            if (getMode() != null) { return Funcs.toStrings(count(Funcs.toDouble(getMode()))); } return null;
        }


        public double getStandardDiv(){
            double stanDiv = 0;
            for (double num : toDouble(toArray())){
                stanDiv += Math.pow(num - (double) getMean(),2);
            }
            return Math.sqrt(stanDiv/size());// or Math.sqrt(getVariance());
        }

        public double getVariance(){
            double mVariance = 0;
            for (double i : toDouble(toArray())) {
                mVariance += (i - (double) getMean()) * (i - (double) getMean());
            }
            mVariance /= size();
            return mVariance;// or getStandardDiv/size();
        }

        public double getCoVariance(Double array[]){
            double sum = 0;
            if (size() == array.length) {
                StatsArray coArray = new StatsArray(array);
                for (int i : range(size())) {
                    sum += ((double) pop(i) - getMean()) * ((double) coArray.pop(i) - coArray.getMean());
                }
            }
            return sum / (size() - 1);
        }

        public Enumerator<Double> getEnumerator(){ return new Enumerator<>(this); }

    }

    public static class BinaryTree<T>{

        private Node<T> HEAD;

        private List<Node<T>> nodeList = new ArrayList<>();

        private Comparator<T> comparator;

        public Node<T> getHead() { return HEAD; }

        public static class Node<T>{

            public T value;
            public int count;
            public Node<T> left;
            public Node<T> right;

            Node(T value){ this.value = value; this.count=1; }

            public String toString(){ return format("Node(value:<>, count:<>)",  value, count); }

            public Node clone(){
                Node<T> n = new Node<>(value);
                n.left = left;
                n.right = right;
                return n;
            }
        }

        public interface Comparator<T>{
            boolean item1_equals_item2(T item1, T item2);
            boolean item1_greaterthan_item2(T item1, T item2);
            boolean item1_lesserthan_item2(T item1, T item2);
        }

        public static class DefaultComparator{

            private DefaultComparator(){}

            public static final Comparator NumberComparator = new Comparator() {
                @Override
                public boolean item1_equals_item2(Object item1, Object item2) { return toDouble(item1).equals(toDouble(item2)); }

                @Override
                public boolean item1_greaterthan_item2(Object item1, Object item2) { return toDouble(item1) > toDouble(item2); }

                @Override
                public boolean item1_lesserthan_item2(Object item1, Object item2) { return toDouble(item1) < toDouble(item2); }
            };

            public static final Comparator<String> StringComparator = new Comparator<>() {
                @Override
                public boolean item1_equals_item2(String item1, String item2) { return item1.equals(item2); }

                @Override
                public boolean item1_greaterthan_item2(String item1, String item2) { return item1.compareTo(item2) > 0; }

                @Override
                public boolean item1_lesserthan_item2(String item1, String item2) { return item1.compareTo(item2) < 0; }
            };

            public static final Comparator<Character> CharacterComparator = new Comparator<>() {
                @Override
                public boolean item1_equals_item2(Character item1, Character item2) { return item1 == item2; }

                @Override
                public boolean item1_greaterthan_item2(Character item1, Character item2) {
                    return item1.toString().compareTo(item2.toString()) > 0;
                }

                @Override
                public boolean item1_lesserthan_item2(Character item1, Character item2) {
                    return item1.toString().compareTo(item2.toString()) > 0;
                }
            };
        }

        public BinaryTree(Comparator<T> comparator){ setComparator(comparator); }

        private void setComparator(Comparator<T> comparator) { this.comparator = comparator; }

        public void add(T value){

            if (HEAD == null){
                HEAD = new Node<>(value);
            }
            else{
                Node<T> current_node = HEAD;

                while (true){
                    if (comparator.item1_equals_item2(value, current_node.value)){
                        current_node.count += 1;break;
                    }
                    else if (comparator.item1_lesserthan_item2(value, current_node.value)){
                        if (current_node.left == null){ current_node.left = new Node<>(value);break; }
                        else{ current_node = current_node.left; }
                    }
                    else if (comparator.item1_greaterthan_item2(value, current_node.value)){
                        if (current_node.right == null){ current_node.right = new Node<>(value);break; }
                        else{ current_node = current_node.right; }
                    }
                }

            }

        }

        public void addAll(T... values){ for (T i : values){ add(i); } }

        public boolean contains(T value){ return get(value) != null; }

        public Node<T> get(T value){
            Node<T> current_node = HEAD;

            while (true) {
                if (current_node == null) {
                    return null;
                } else if (comparator.item1_equals_item2(value, current_node.value)) {
                    return current_node;
                } else if (comparator.item1_lesserthan_item2(value, current_node.value)) {
                    current_node = current_node.left;
                } else if (comparator.item1_greaterthan_item2(value, current_node.value)) {
                    current_node = current_node.right;
                }
            }

        }

        private Node<T> getParentNodeFor(T value){

            Node<T> current_node = HEAD;

            if (current_node != null && current_node.value == value){
                return current_node;
            }

            while (true) {
                if (current_node.left != null && comparator.item1_equals_item2(value, current_node.left.value)) {
                    return current_node;
                } else if (current_node.right != null && comparator.item1_equals_item2(value, current_node.right.value)) {
                    return current_node;
                } else if (comparator.item1_lesserthan_item2(value, current_node.value)) {
                    current_node = current_node.left;
                } else if (comparator.item1_lesserthan_item2(value, current_node.value)) {
                    current_node = current_node.right;
                }
            }

        }

        private Node<T> getRightmostNodeFor(Node<T> current_node){
            while (current_node.right != null){
               current_node = current_node.right;
            }
            return current_node;
        }

        private boolean hasNoChildNodes(Node<T> node){
            if (node.left == null && node.right == null){ return true; }
            else{ return false; }
        }

        private boolean hasOneChildNodes(Node<T> node){
            if (node.left == null && node.right != null){ return true; }
            else if (node.left != null && node.right == null){ return true; }
            else{ return false; }
        }

        private boolean hasTwoChildNodes(Node<T> node){
            if (node.left != null && node.right != null){ return true; }
            else{ return false; }
        }

        public void delete(T value){
            if (contains(value)){

                Node<T> temp =  get(value);
                Node<T> temp_parent = getParentNodeFor(temp.value);

                if (temp.count > 1){
                    temp.count -= 1;
                }
                else if (hasNoChildNodes(temp)){
                    if (temp_parent.right == temp){ temp_parent.right = null; }
                    else if (temp_parent.left == temp) { temp_parent.left = null; }
                    else{ HEAD = null; }
                    //unlink node from parent
                }
                else if (hasOneChildNodes(temp)){
                    if (temp_parent.left == temp){ temp_parent.left = (temp.left != null) ? temp.left : temp.right; }
                    else if (temp_parent.right == temp) { temp_parent.right = (temp.left != null) ? temp.left : temp.right; }
                    else{ HEAD = (temp.left != null) ? temp.left : temp.right; }
                    //point parent node of temp to temp.child
                }
                else{

                    Node<T> rightmost_node = getRightmostNodeFor(temp.left);
                    Node<T> rightmost_node_parent = getParentNodeFor(rightmost_node.value);

                    if (rightmost_node_parent != temp) {
                        rightmost_node_parent.right = rightmost_node.left;
                        rightmost_node.left = temp.left;
                    }

                    rightmost_node.right = temp.right;

                    if (temp == temp_parent.left){ temp_parent.left = rightmost_node; }
                    else if (temp == temp_parent.right){ temp_parent.right = rightmost_node; }
                    else{ HEAD = rightmost_node; }

                    //get the rightmost (largest) node in the left branch and set it to temp
                    // or the leftmost (smallest) node in the right branch and set it to temp
                }
            }
        }

        public void deleteAll(T value){ while (contains(value)){ delete(value); } }

        public void replace(T value, T new_value){
            if (contains(value)){
                delete(value);
                add(new_value);
            }
        }

        public void replaceAll(T value, T new_value){
            if (contains(value)){
                int count = get(value).count;
                deleteAll(value); add(new_value);
                get(value).count = count;
            }
        }

        public int size(){ return toListInOrder().size(); }

        public boolean isEmpty(){ return HEAD == null; }

        public void clear(){ HEAD = null; }

        public Node[] toArray(){
            nodeList.clear();
            buildArray(HEAD);
            Node<T>[] n = new Node[nodeList.size()];
            n = nodeList.toArray(n);
            return n;
        }

        public List<Node<T>> toListInOrder(){
            nodeList.clear();
            buildArray(HEAD);
            return nodeList;
        }

        private void buildArray(Node current_node){
            if (current_node == null){ return; }
            buildArray(current_node.left);
            for (int i=0;i<current_node.count;i++){ nodeList.add(current_node); }
            buildArray(current_node.right);
        }

        public void printInorder(){ inOrder(HEAD); }
        public void printPreorder(){ preOrder(HEAD); }
        public void printPostorder(){ postOrder(HEAD); }


        private void inOrder(Node head){
            if (head == null){ return; }
            else{
                inOrder(head.left);
                print(head);print();
                inOrder(head.right);
            }
        }

        private void preOrder(Node head){
            if (head == null){ return; }
            else{
                print(head);print();
                preOrder(head.left);
                preOrder(head.right);
            }
        }

        private void postOrder(Node head){
            if (head == null){ return; }
            else{
                postOrder(head.left);
                postOrder(head.right);
                print(head);print();
            }
        }

        public String toString(){
            return format("BinaryTree(Head : <>)", HEAD.toString());
        }

    }

    public static class Stack<T>{

        protected MasterList<T> list = new MasterList();

        public void push(T value){ list.insert(0,value); }

        public T pop(){ T top = list.popfirst();list.remove(0);return top; }

        public T peekTop(){ return list.popfirst(); }

        public int size(){ return list.size(); }

        public boolean isEmpty(){return list.isEmpty();}

        public void clear() { list.clear(); }

        public String toString(){ return list.toString();}

    }

    public static class StackList<T> extends Stack<T> implements Iterable<T>{

        public StackList(){}

        public StackList(Stack<T> stack) { for (int i : range(stack.size())){ push(stack.pop()); } }

        public StackList(List<T> list) { for (int i : range(list.size())){ push(list.get(i)); } }

        public T peekElementAt(int index){ return super.list.pop(index); }

        public T pop(int index){ T temp = super.list.pop(index); super.list.remove(index); return temp;  }

        public void replace(int index, T object){ super.list.update(index, object); }

        public void remove(int index){ super.list.remove(index); }

        public void remove(T object){ super.list.remove(super.list.firstIndexOf(object)); }

        public void insert(int index, T object){ super.list.insert(index, object); }

        public boolean contains(T object){ return super.list.contains(object); }

        public int findFirstIndexOf(T object){ return super.list.firstIndexOf(object); }

        public int findLastIndexOf(T object){ return super.list.lastIndexOf(object); }

        public List<T> toList(){ return super.list.toList(); }

        public Stack<T> toStack(){ return this; }

        public Enumerator<T> getEnumerator(){ return new Enumerator<>(toList()); }

        @Override
        public Iterator<T> iterator() { return super.list.iterator(); }

        @Override
        public void forEach(Consumer<? super T> action) { super.list.forEach(action); }

        @Override
        public Spliterator<T> spliterator() { return list.spliterator(); }
    }
    
    public static class LinkedList<T> implements Iterable<T>{

        private int SIZE = 0;

        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {

                Enumerator<T> enumerator  = getEnumerator();

                @Override
                public boolean hasNext() {
                    return enumerator.hasNext();
                }

                @Override
                public T next() {
                    return enumerator.getNext();
                }
            };
        }

        @Override
        public void forEach(Consumer<? super T> action) {
            Enumerator<T> enumerator = getEnumerator();
            while (enumerator.hasNext()){ action.accept(enumerator.getNext()); }
        }


        private class Node{

            public T value;
            public Node previous;
            public Node next;

            Node(T value){ this.value = value; }

            public String toString(){ return format("Node(value:<>)", value); }

            public Node clone(){
                Node n = new Node(value);
                n.previous = previous;
                n.next = next;
                return n;
            }

        }

        private Node head = null;

        LinkedList(){}

        public void append(T value){
            if (isEmpty()){
                head = new Node(value); SIZE++;
            }
            else{
                getTempAt(size()-1).next = new Node(value); SIZE++;
            }
        }

        public void insert(int index, T value){
            if (validateIndex(index)){

                if (index == 0){
                   Node temp = new Node(value);temp.next = head;
                   head = temp; SIZE++;
                   return;
                }

                Node new_node = new Node(value);
                new_node.next = getTempAt(index-1).next;
                getTempAt(index-1).next = new_node;

                SIZE++;
            }
        }

        public void replace(int index, T value){
            if (validateIndex(index)){ getTempAt(index).value = value; }
        }

        public void replaceFirst(T object, T value){
            int found = findFirstIndexOf(object);
            if (found != -1){ replace(found, value); }
        }

        public void replaceLast(T object, T value){
            int found = findLastIndexOf(object);
            if (found != -1){ replace(found, value); }
        }

        public void replaceAll(T object, T value){

            if (findLastIndexOf(object) != -1 && get(findFirstIndexOf(object)) == value) { return; }

            while (findFirstIndexOf(object) != -1){
                replace(findFirstIndexOf(object), value);
            }
        }

        public void remove(int index){
            if (validateIndex(index)){
                if (index == 0){
                    head = head.next;SIZE--;
                    return;
                }
                getTempAt(index-1).next = getTempAt(index-1).next.next;
                SIZE--;
            }
        }

        public void removeObject(T object){
           int found = findFirstIndexOf(object);
           if (found == -1){ return; }
           else{ remove(found); }
        }

        public void removeAllOf(T object){
           while (findFirstIndexOf(object) != -1){ removeObject(object); }
        }

        public T get(int index){
            if (validateIndex(index)){
                return getTempAt(index).value;
            }
            return null;
        }

        public int findFirstIndexOf(T object){
            if (!isEmpty()){
                for (int i : range(size())) {
                    if (get(i) == object){ return i; }
                }
            }
            return -1;
        }

        public int findLastIndexOf(T object){
            if (!isEmpty()){
                for (int i : range(size())) {
                    if (get((SIZE-1)-i) == object){ return (SIZE-1)-i; }
                }
            }
            return -1;
        }

        public int count(T object){
            int counter = 0;
            for (int i : range(size())){
                if (get(i) == object){ counter++; }
            }
            return counter;
        }

        public int size(){  return SIZE; }

        public void clear(){  head = null; SIZE = 0; }

        public boolean isEmpty(){ return size() == 0; }

        public Enumerator<T> getEnumerator(){ return new Enumerator<>(this); }

        public String toString(){
            String list = "LinkedList[";
            for (int i : range(size())){
                list += get(i)+ ((i == size()-1) ? "":", ");
            }
            return list + "]";
        }

        private boolean validateIndex(int index){
            if (index < 0 || index >= SIZE){
                throw new IndexOutOfBoundsException();
            }
            else{ return true; }
        }

        private Node getTempAt(int index){
            Node temp = head;

            for (int i : range(index)){
                temp = temp.next;
            }

            return temp;
        }

    }

    public static class MasterQueue<T> implements Iterable<T>{

        private LinkedList<T> DATA = new LinkedList<>();

        MasterQueue(){}

        public void add(T object){ DATA.append(object); }

        public T getNext(){ T temp = peekNext(); DATA.remove(0); return temp; }

        public T peekNext(){
            if (isEmpty()){
                try {
                    throw new EmptyObjectException("MasterQueue");
                } catch (EmptyObjectException e) {
                    e.printStackTrace();
                }
            }
            return DATA.get(0);
        }

        public boolean hasNext(){ return !isEmpty();}

        public boolean isEmpty(){ return DATA.isEmpty();}

        public int size(){ return DATA.size();}

        public String toString(){ return DATA.toString().replace("LinkedList", "MasterQueue");}

        @Override
        public Iterator<T> iterator() { return DATA.iterator(); }
    }

    public static class Queue<T> implements Iterable<T>{

        private LinkedList<T> DATA = new LinkedList<>();

        Queue(){}

        public void enqueue(T object){ DATA.append(object); }

        public T dequeue(){ T temp = peekFront(); DATA.remove(0); return temp; }

        public T peekFront(){
            if (isEmpty()){
                try {
                    throw new EmptyObjectException("Queue");
                } catch (EmptyObjectException e) {
                    e.printStackTrace();
                }
            }
            return DATA.get(0);
        }

        public boolean isEmpty(){ return DATA.isEmpty();}

        public int size(){ return DATA.size();}

        public String toString(){ return DATA.toString().replace("LinkedList", "Queue");}

        @Override
        public Iterator<T> iterator() { return DATA.iterator(); }
    }

    public static class Deque<T> implements Iterable<T>{

        private LinkedList<T> DATA = new LinkedList<>();

        Deque(){}

        public void enqueueEnd(T object){ DATA.append(object); }

        public void enqueueStart(T object){ DATA.insert(0,object); }

        public T dequeueEnd(){ T temp = peekBack(); DATA.remove(size()-1); return temp; }

        public T dequeueStart(){ T temp = peekFront(); DATA.remove(0); return temp; }

        public T peekFront(){
            if (isEmpty()){
                try {
                    throw new EmptyObjectException("Deque");
                } catch (EmptyObjectException e) {
                    e.printStackTrace();
                }
            }
            return DATA.get(0);
        }

        public T peekBack(){
            if (isEmpty()){
                try {
                    throw new EmptyObjectException("Deque");
                } catch (EmptyObjectException e) {
                    e.printStackTrace();
                }
            }
            return DATA.get(size()-1);
        }

        public boolean isEmpty(){ return DATA.isEmpty();}

        public int size(){ return DATA.size();}

        public String toString(){ return DATA.toString().replace("LinkedList", "Deque");}

        @Override
        public Iterator<T> iterator() { return DATA.iterator(); }

        @Override
        public void forEach(Consumer<? super T> action) { DATA.forEach(action); }
    }

    public static class JSONData implements Iterable<KeyValuePair> {

        Dictionary DATA = new Dictionary();

        private boolean isJsonString = false;

        public static class JSONException extends Exception{ }

        public JSONData() { }

        public JSONData(Object jsonString) throws JSONException {
            isJsonString = !isJsonString;
            buildJsonArray(jsonString.toString().trim());
            isJsonString = !isJsonString;
        }

        public JSONData(Map mapData){ for (Object key : mapData.keySet()){ add(key,mapData.get(key)); } }

        public JSONData(Dictionary dictionaryData) {
            List<KeyValuePair> list = dictionaryData.getList();
            for(KeyValuePair o : list) { add(o.getKey(),o.getValue()); }
        }

        private void buildJsonArray(String jsonstring) throws JSONException {
            if (validateJson(jsonstring)) {
                jsonstring = jsonstring.substring(1, jsonstring.length() - 1).trim();

                KeyValuePair kv = new KeyValuePair();
                Stack<Character> s = new Stack<>();
                List<String> stringList = new ArrayList<>();
                StringBuilder data = new StringBuilder();

                //breaking string by comma
                for (Character c : jsonstring.toCharArray()) {

                    if (!s.isEmpty() && c == s.peekTop()){
                        s.pop(); data.append(c);
                    }
                    else if (!s.isEmpty() && ((s.peekTop() == '{' && c == '}') ||(s.peekTop() == '(' && c == ')') ||(s.peekTop() == '[' && c == ']'))){
                        s.pop(); data.append(c);
                    }
                    else if (c == '\'' || c == '"'){
                        s.push(c); data.append(c);
                    }
                    else if ((s.isEmpty() || (s.peekTop() != '"' && s.peekTop() != '\'')) && (c == '{' || c == '[' || c == '(')){
                        s.push(c); data.append(c);
                    }
                    else if (c == ',' && s.isEmpty()) {
                        stringList.add(data.toString().trim());
                        data = new StringBuilder();
                    }
                    else {
                        data.append(c);
                    }

                }
                if (data.length() > 0){ stringList.add(data.toString().trim()); }


                //breaking string into key values
                for (String line : stringList){
                    s.clear();data = new StringBuilder();
                    for (Character c : line.toCharArray()){
                        if (!s.isEmpty() && s.peekTop() != ':' && c == s.peekTop()){
                            s.pop(); data.append(c);
                        }
                        else if (s.isEmpty() && (c == '\'' || c == '"')){
                            s.push(c); data.append(c);
                        }
                        else if (c == ':' && s.isEmpty()) {
                            s.push(c); kv.setKey(data.toString().trim());
                            data = new StringBuilder();
                        }
                        else {
                            data.append(c);
                        }

                    }
                    if (data.length() > 0){
                        kv.setValue(data.toString().trim());
                    }
                    add(kv.getKey(), kv.getValue());
                }


            }
            else{
                throw new JSONException();
            }
        }

        private boolean validateJson(String jsonstring) {
            if (jsonstring != null && jsonstring.charAt(0) == '{' && jsonstring.charAt(jsonstring.length()-1) == '}'){
                if (jsonstring.contains(":") || jsonstring.contains(",")){
                    return true;
                }
                return false;
            }
            return false;
        }

        private boolean isString(String line){
            line = line.trim();
            if (line.charAt(0) == '"' && line.charAt(line.length()-1) == '"'){ return true; }
            return false;
        }

        public JSONData add(Object key, Object value) {
            if (isJsonString){ DATA.add(key, value); }
            else{ DATA.add(prepareObject(key),prepareObject(value)); }
            return this;
        }

        public void addIfAbsent(Object key, Object value){ DATA.addIfAbsent(prepareObject(key),prepareObject(value)); }

        public Object get(Object key) { return getObject(DATA.get(prepareObject(key))); }

        public Object getOrDefault(Object key, Object defaultValue) {
            if (get(key) == null){ return defaultValue; }
            return get(key);
        }

        private Object prepareObject(Object line){
            if (line != null && line.getClass().getSimpleName().equals("String")){ line = '"'+line.toString()+'"'; }
            return line;
        }

        private Object getObject(Object line){
            if (line != null && isString(line.toString())){
                return line.toString().substring(1,line.toString().length()-1);
            }
            return line;
        }

        public String getString(Object key){ return toStrings(get(key)); }
        public String getStringOrDefault(Object key, Object defaultValue){
            return toStrings(getOrDefault(key, defaultValue));
        }

        public Character getCharacter(Object key){ return toCharacter(get(key)); }
        public Character getCharacterOrDefault(Object key, Object defaultValue){
            return toCharacter(getOrDefault(key,defaultValue));
        }

        public Integer getInteger(Object key){ return toInteger(get(key)); }
        public Integer getIntegerOrDefault(Object key, Object defaultValue){
            return toInteger(getOrDefault(key,defaultValue));
        }

        public Double getDouble(Object key){ return toDouble(get(key)); }
        public Double getDoubleOrDefault(Object key, Object defaultValue){
            return toDouble(getOrDefault(key,defaultValue));
        }

        public Float getFloat(Object key){ return toFloat(get(key)); }
        public Float getFloatOrDefault(Object key, Object defaultValue){
            return toFloat(getOrDefault(key,defaultValue));
        }

        public Boolean getBoolean(Object key){ return toBoolean(get(key)); }
        public Boolean getBooleanOrDefault(Object key, Object defaultValue){
            return toBoolean(getOrDefault(key,defaultValue));
        }

        public JSONData getJSONObject(Object key) throws JSONException { return new JSONData(get(key)); }

        public Object[] getArray(Object key) { return getArrayFromString(getString(key)).toArray(); }

        private List getArrayFromString(String stringToConvert){
            Dictionary<Character, Character> dictionary = new Dictionary<>();
            dictionary.add('{','}');dictionary.add('(',')');dictionary.add('[',']');
            dictionary.add('\'','\'');dictionary.add('"','"');
            String string = prepareString(stringToConvert);
            List stringList = new ArrayList<>();
            Stack<Character> s = new Stack<>();
            StringBuilder data = new StringBuilder();

            for (Character c : string.toCharArray()) {

                if (!s.isEmpty() && dictionary.get(s.peekTop()) == c){
                    s.pop(); data.append(c);
                }
                else if (dictionary.hasKey(c) && (s.isEmpty() || (!s.isEmpty() && !(s.peekTop() == '"' || s.peekTop() == '\'')))){
                    s.push(c); data.append(c);
                }
                else if (c == ',' && s.isEmpty()) {
                    stringList.add(data.toString().trim());
                    data = new StringBuilder();
                }
                else {
                    data.append(c);
                }

            }

            if (data.length() > 0){ stringList.add(data.toString().trim()); }

            return stringList;
        }

        private String prepareString(String data) {
            Dictionary<Character, Character> dictionary = new Dictionary<>();
            dictionary.add('{','}');dictionary.add('(',')');dictionary.add('[',']');
            if (dictionary.keys().contains(data.charAt(0))){
                if (data.charAt(data.length()-1) == dictionary.get(data.charAt(0))){
                    data = data.substring(1,data.length()-1).trim();
                }
            }
            return data;
        }

        public boolean isEmpty(){ return DATA.isEmpty(); }
        public int size(){ return DATA.size(); }
        public void clear(){ DATA.clear(); }
        public void remove(Object key){ DATA.remove(prepareObject(key)); }
        public void remove(Object key,Object value){ DATA.remove(prepareObject(key),prepareObject(value)); }
        public boolean hasKey(Object key){ return DATA.hasKey(prepareObject(key)); }
        public boolean hasValue(Object value){ return DATA.hasValue(prepareObject(value)); }
        public boolean hasKeyValue(Object key, Object value){ return DATA.hasKeyValue(prepareObject(key),prepareObject(value)); }
        public void replace(Object key, Object value){ DATA.replace(prepareObject(key),prepareObject(value)); }
        public void replace(Object key,Object value, Object new_value){
            DATA.replace(prepareObject(key),prepareObject(value), prepareObject(new_value));
        }

        public List<KeyValuePair> getAsList(){
            List<KeyValuePair> list = DATA.getList();
            List<KeyValuePair> new_list = new ArrayList<>();
            for (KeyValuePair o : list){
                new_list.add(new KeyValuePair<>(getObject(o.getKey()),getObject(o.getValue())));
            }
            return new_list;
        }

        public List keys(){
            List list = new ArrayList();
            for (KeyValuePair pair : getAsList()){ list.add(pair.getKey()); }
            return list;
        }

        public Collection values(){
            List new_collection = new ArrayList();
            for (Object o : DATA.values()){ new_collection.add(getObject(o)); }
            return new_collection;
        }

        public void forEach(BiConsumer action){
            for (KeyValuePair kv : getAsList()){ action.accept(getObject(kv.getKey()),getObject(kv.getValue())); }
        }

        @Override
        public Iterator<KeyValuePair> iterator() { return DATA.iterator(); }

        @Override
        public void forEach(Consumer<? super KeyValuePair> action) { DATA.forEach(action); }

        public Enumerator<KeyValuePair> getEnumeration(){ return new Enumerator<KeyValuePair>(getAsList()); }

        @Override
        public String toString() {
            String data = "{";
            Enumerator<KeyValuePair> list = DATA.getEnumerator() ;
            while (list.hasNext()){
                KeyValuePair x = list.getNext();
                data += format("\n\t<>: <>",x.getKey(),x.getValue());
                if (list.hasNext()){ data += ",";}
            }
            return data+"\n}";
        }
    }

    public static class XMLObject {

        private String PARENTTAG;
        private String RAWCHILD;
        private Dictionary PROPERTIES = new Dictionary();
        private List<XMLObject> CHILDREN = new ArrayList<>();

        public XMLObject() { }

        public XMLObject(String parentTag) { setParentTAG(parentTag); }

        private boolean isString(String line){
            line = line.trim();
            if (line.charAt(0) == '"' && line.charAt(line.length()-1) == '"'){ return true; }
            return false;
        }

        public void setParentTAG(String parentTag){ PARENTTAG = parentTag; }

        public String getParentTAG(){ return PARENTTAG; }

        public XMLObject addProperty(Object propertyName, Object propertyValue){
            PROPERTIES.add(propertyName,propertyValue); return this;
        }

        public Dictionary getProperties(){ return PROPERTIES; }

        public XMLObject addChild(XMLObject child){ CHILDREN.add(child); return this; }

        public XMLObject addChildren(XMLObject... children){
            for (XMLObject child : children){ addChild(child); } return this;
        }

        public void setProperty(Dictionary property){ PROPERTIES = property; }
        public void setChildren(List<XMLObject> children){ CHILDREN = children; }

        public XMLObject setRawChild(String rawChild){ RAWCHILD = rawChild; return this; }

        public List<XMLObject> getALLChildren(){ return CHILDREN; }

        public List<XMLObject> getChildrenWithTAG(String childTagName){
            List<XMLObject> MATCHLIST = new ArrayList<>();
            for (XMLObject child : CHILDREN){
                if (child.getParentTAG().equals(childTagName)){ MATCHLIST.add(child); }
            }
            return MATCHLIST;
        }

        public List<XMLObject> getSubChildrenWithTAG(String childTagName){
            List<XMLObject> MATCHLIST = new ArrayList<>();
            for (XMLObject child : CHILDREN){
                if (child.getParentTAG().equals(childTagName)){
                    MATCHLIST.add(child);
                }
                List CHILDSMATCHLIST = child.getSubChildrenWithTAG(childTagName);
                MATCHLIST.addAll(CHILDSMATCHLIST);
            }
            return MATCHLIST;
        }

        public Object getPropertyValue(String propertyName){
            return PROPERTIES.get(propertyName);
        }

        public boolean hasProperty(String propertyName){
            for (Object property : PROPERTIES.keys()){
                if (property == propertyName){ return true; }
            }
            return false;
        }

        public boolean hasChild(XMLObject object){
            for (XMLObject child : CHILDREN){
                if (child == object){ return true; }
            }
            return false;
        }

        public boolean hasChildWithTAG(String childTagName){
            for (XMLObject child : CHILDREN){
                if (child.getParentTAG().equals(childTagName)){ return true; }
            }
            return false;
        }

        public boolean hasSubChildWithTAG(String childTagName){ return containsSubChild(childTagName) != null; }

        public boolean hasSubChild(XMLObject child){ return containsSubChild(child) != null; }

        public void replaceAllChildrenWithTAG(String childTagName, String newChildTagName){
            if (hasChildWithTAG(childTagName)) {
                for (XMLObject child : CHILDREN) {
                    if (child.getParentTAG().equals(childTagName)) {
                        int index = CHILDREN.indexOf(child);
                        CHILDREN.remove(index);
                        child.setParentTAG(newChildTagName);
                        CHILDREN.add(index, child);
                    }
                }
            }
        }

        public void replaceAllSubChildrenWithTAG(String childTagName, String newChildTagName){
            if (hasSubChildWithTAG(childTagName)) {
                for (XMLObject child : CHILDREN) {
                    if (child.getParentTAG().equals(childTagName)) {
                        int index = CHILDREN.indexOf(child);
                        CHILDREN.remove(index);
                        child.setParentTAG(newChildTagName);
                        CHILDREN.add(index, child);
                    }
                    child.replaceAllSubChildrenWithTAG(childTagName, newChildTagName);
                }
            }
        }

        public void removeAllChildrenWithTAG(String childTagName){
            List<XMLObject> DATA = new ArrayList<>();DATA.addAll(CHILDREN);
            for (XMLObject child : DATA){
                if (child.getParentTAG().equals(childTagName)) { CHILDREN.remove(child); }
            }
        }

        public void removeAllSubChildrenWithTAG(String childTagName){
            List<XMLObject> DATA = new ArrayList<>();DATA.addAll(CHILDREN);

            for  (XMLObject child : DATA){
                if (child.getParentTAG().equals(childTagName)) {
                    CHILDREN.remove(child);
                } else {
                    if (child.hasSubChildWithTAG(childTagName)) {
                        child.removeAllSubChildrenWithTAG(childTagName);
                    }
                }
            }
        }

        private XMLObject containsSubChild(String childTagName){
            for (XMLObject child : CHILDREN){
                if (child.getParentTAG().equals(childTagName)){ return child; }
                else{ if (child.containsSubChild(childTagName) != null){ return child; } }
            }
            return null;
        }

        private XMLObject containsSubChild(XMLObject object){
            for (XMLObject child : CHILDREN){
                if (child == object){ return child; }
                else{ if (child.containsSubChild(object) != null){ return child; } }
            }
            return null;
        }

        private String getPropertyString(){
            String property = "";

            Enumerator<KeyValuePair> enumerator= PROPERTIES.getEnumerator();

            while (enumerator.hasNext()) {
                KeyValuePair x = enumerator.getNext();
                property += " "+x.getKey().toString()+"=\""+x.getValue().toString()+'"';
            }
            if (!property.isEmpty()){ property += " "; }

            return property;
        }

        private String getChildrenString(){

            String children = "";

            if (RAWCHILD != null){
                children += "\n"+RAWCHILD+"\n";
            }else {
                for (XMLObject x : CHILDREN) {
                    children += "\n" + x.toString();
                }

                if (!children.isEmpty()) {
                    children += "\n";
                }
            }

            return children;
        }

        public boolean isEmpty(){ return PARENTTAG == null; }

        public void reset(){ PARENTTAG = null;CHILDREN.clear();PROPERTIES.clear();RAWCHILD = null; }

        public void resetChildren(){ CHILDREN.clear();RAWCHILD = null; }

        public void resetProperties(){ PROPERTIES.clear(); }

        public String toString(){
            if (PARENTTAG != null) {
                return format("<<1><2>><3></<1>>", getParentTAG(), getPropertyString(), getChildrenString());
            }
            return null;
        }
    }

}
