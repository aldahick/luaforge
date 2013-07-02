/*
 * Copyright  2000-2004 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License. 
 *
 */
package tiin57.lib.bcel.generic;

/**
 * Super class for instructions dealing with array access such as IALOAD.
 *
 * @version $Id: ArrayInstruction.java 386056 2006-03-15 11:31:56Z tcurdt $
 * @author  <A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>
 */
public abstract class ArrayInstruction extends Instruction implements ExceptionThrower,
        TypedInstruction {

    /**
     * Empty constructor needed for the Class.newInstance() statement in
     * Instruction.readInstruction(). Not to be used otherwise.
     */
    ArrayInstruction() {
    }


    /**
     * @param opcode of instruction
     */
    protected ArrayInstruction(short opcode) {
        super(opcode, (short) 1);
    }


    public Class[] getExceptions() {
        return tiin57.lib.bcel.ExceptionConstants.EXCS_ARRAY_EXCEPTION;
    }


    /** @return type associated with the instruction
     */
    public Type getType( ConstantPoolGen cp ) {
        switch (opcode) {
            case tiin57.lib.bcel.Constants.IALOAD:
            case tiin57.lib.bcel.Constants.IASTORE:
                return Type.INT;
            case tiin57.lib.bcel.Constants.CALOAD:
            case tiin57.lib.bcel.Constants.CASTORE:
                return Type.CHAR;
            case tiin57.lib.bcel.Constants.BALOAD:
            case tiin57.lib.bcel.Constants.BASTORE:
                return Type.BYTE;
            case tiin57.lib.bcel.Constants.SALOAD:
            case tiin57.lib.bcel.Constants.SASTORE:
                return Type.SHORT;
            case tiin57.lib.bcel.Constants.LALOAD:
            case tiin57.lib.bcel.Constants.LASTORE:
                return Type.LONG;
            case tiin57.lib.bcel.Constants.DALOAD:
            case tiin57.lib.bcel.Constants.DASTORE:
                return Type.DOUBLE;
            case tiin57.lib.bcel.Constants.FALOAD:
            case tiin57.lib.bcel.Constants.FASTORE:
                return Type.FLOAT;
            case tiin57.lib.bcel.Constants.AALOAD:
            case tiin57.lib.bcel.Constants.AASTORE:
                return Type.OBJECT;
            default:
                throw new ClassGenException("Oops: unknown case in switch" + opcode);
        }
    }
}
