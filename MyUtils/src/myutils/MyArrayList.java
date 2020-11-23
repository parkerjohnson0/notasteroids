/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutils;

/**
 *
 * @author peppe
 */
public class MyArrayList implements MyList {

    private Object[] objArray = new Object[1];
    private int size;

    public MyArrayList() {
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object get(int index) {
        return objArray[index];
    }

    @Override
    public void add(Object o) {
        if (this.size() == objArray.length) {
            int i = 0;
            Object[] newObjArray = new Object[objArray.length * 2];
            while (i < size) {
                newObjArray[i] = get(i);
                i++;
            }
            newObjArray[size] = o;
            objArray = newObjArray;
            size++;
        } else {
            int i = 0;
            Object[] newObjArray = new Object[objArray.length];
            while (i < size) {
                newObjArray[i] = get(i);
                i++;
            }
            newObjArray[size] = o;
            objArray = newObjArray;
            size++;
        }
    }

    @Override
    public void add(int index, Object o) {
        boolean added = false;
        if (size == objArray.length) {
            int i = 0;
            Object[] newObjArray = new Object[size * 2];
            while (i < this.size()) {
                if (i == index) {
                    newObjArray[index] = o;
                    i++;
                    added = true;
                    size++;
                    continue;
                } else if (i != index && !added) {
                    newObjArray[i] = get(i);
                    i++;
                } else if (i != index && added) {
                    newObjArray[i] = get(i - 1);
                    i++;
                }

            }

            objArray = newObjArray;

        } else {
            int i = 0;
            Object[] newObjArray = new Object[objArray.length];
            while (i < size) {
                if (i == index) {
                    newObjArray[index] = o;
                    i++;
                    added = true;
                    size++;
                    continue;
                } else if (i != index && !added) {
                    newObjArray[i] = get(i);
                    i++;
                } else if (i != index && added) {
                    newObjArray[i] = get(i - 1);
                    i++;
                }

            }

            objArray = newObjArray;

        }

    }

    @Override
    public void remove(int index) {
        boolean removed = false;
        Object[] newObjArray = new Object[size];

        for (int i = 0; i < size; i++) {
            if (i == index) {
                if (i == size - 1) {
                    newObjArray[i] = objArray[i];
                } else {
                    newObjArray[i] = objArray[i + 1];

                }
                size--;
                removed = true;
            } else if (i != index && !removed) {
                newObjArray[i] = objArray[i];
            } else if (i != index && removed) {
                newObjArray[i] = objArray[i + 1];
            }

        }
        objArray = newObjArray;
    }

    @Override
    public void remove(Object o) {
        boolean removed = false;
        Object[] newObjArray = new Object[size];

        for (int i = 0; i < size; i++) {
            if (get(i) == o) {
                newObjArray[i] = objArray[i + 1];
                removed = true;
            } else if (get(i) != o && !removed) {
                newObjArray[i] = objArray[i];
            } else if (get(i) != o && removed) {
                newObjArray[i] = objArray[i + 1];
            }

        }
        objArray = newObjArray;
    }

    @Override
    public void set(int index, Object o) {
        objArray[index] = o;
    }

    @Override
    public boolean contains(Object o) {
        for (Object obj : objArray) {
            if (obj == o) {
                return true;
            }

        }
        return false;
    }

    @Override
    public int indexOfObject(Object o) {
        int index = 0;
        for (Object obj : objArray) {
            if (obj == o) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        for (Object obj : objArray) {
            if (obj != null) {
                return false;
            }

        }
        return true;
    }

    @Override
    public void removeAll() {
        Object[] newObjArray = new Object[this.size()];

        objArray = newObjArray;
        size = 0;
    }

    @Override
    public String toString() {
        String str = "";
        int i = 0;
        while (get(i) != null) {
            str += get(i) + "\n";
            i++;
        }

        return str;

    }

}
