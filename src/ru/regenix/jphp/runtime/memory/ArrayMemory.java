package ru.regenix.jphp.runtime.memory;

import ru.regenix.jphp.runtime.type.HashTable;

public class ArrayMemory extends Memory {

    public HashTable value;

    public ArrayMemory() {
        super(Type.ARRAY);
        value = new HashTable();
    }

    public ArrayMemory(HashTable table){
        super(Type.ARRAY);
        value = table;
    }

    public static Memory valueOf(){
        return new ArrayMemory();
    }

    public static Memory valueOf(HashTable table){
        return new ArrayMemory(table);
    }

    @Override
    public Memory toImmutable() {
        value.addCopy();
        return new ArrayMemory(value);
    }

    @Override
    public long toLong() {
        return value.size() == 0 ? 0 : 1;
    }

    @Override
    public double toDouble() {
        return value.size() == 0 ? 0 : 1;
    }

    @Override
    public boolean toBoolean() {
        return value.size() != 0;
    }

    @Override
    public Memory toNumeric() {
        return value.size() == 0 ? CONST_INT_0 : CONST_INT_1;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public Memory inc() {
        return toNumeric().inc();
    }

    @Override
    public Memory dec() {
        return toNumeric().dec();
    }

    @Override
    public Memory negative() {
        return toNumeric().negative();
    }

    @Override
    public Memory plus(Memory memory) {
        return toNumeric().plus(memory);
    }

    @Override
    public Memory minus(Memory memory) {
        return toNumeric().minus(memory);
    }

    @Override
    public Memory mul(Memory memory) {
        return toNumeric().mul(memory);
    }

    @Override
    public Memory div(Memory memory) {
        return toNumeric().div(memory);
    }

    @Override
    public Memory mod(Memory memory) {
        return toNumeric().mod(memory);
    }

    @Override
    public boolean equal(Memory memory) {
        if (memory.type == Type.ARRAY){
            try {
                return value.compare(this, (ArrayMemory)memory) == 0;
            } catch (HashTable.UncomparableArrayException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean notEqual(Memory memory) {
        return !equals(memory);
    }

    @Override
    public boolean smaller(Memory memory) {
        if (memory.type == Type.ARRAY){
            try {
                return value.compare(this, (ArrayMemory)memory) < 0;
            } catch (HashTable.UncomparableArrayException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean smallerEq(Memory memory) {
        if (memory.type == Type.ARRAY){
            try {
                return value.compare(this, (ArrayMemory)memory) <= 0;
            } catch (HashTable.UncomparableArrayException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean greater(Memory memory) {
        if (memory.type == Type.ARRAY){
            try {
                return value.compare(this, (ArrayMemory)memory) > 0;
            } catch (HashTable.UncomparableArrayException e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean greaterEq(Memory memory) {
        if (memory.type == Type.ARRAY){
            try {
                return value.compare(this, (ArrayMemory)memory) >= 0;
            } catch (HashTable.UncomparableArrayException e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return value.size() == 0 ? 0 : 1;
    }

    @Override
    public void unset() {
        value = null;
    }

    @Override
    public Memory valueOfIndex(Memory index) {
        return value.get(this, index);
    }

    @Override
    public Memory valueOfIndex(long index) {
        return value.getByScalar(this, LongMemory.valueOf(index));
    }

    @Override
    public Memory valueOfIndex(double index) {
        return value.getByScalar(this, LongMemory.valueOf((long)index));
    }

    @Override
    public Memory valueOfIndex(boolean index) {
        return value.getByScalar(this, index ? CONST_INT_0 : CONST_INT_1);
    }

    @Override
    public Memory valueOfIndex(String index) {
        return value.getByScalar(this, index);
    }
}
