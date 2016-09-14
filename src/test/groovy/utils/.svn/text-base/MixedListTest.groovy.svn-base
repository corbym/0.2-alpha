/** Copyright 2008 Matthew Corby-Eaglen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
package utils

import junit.framework.TestCase
import org.groovymud.object.AbstractMudObject
import org.groovymud.utils.Dice
/**
 * @author corbym
 *
 */
public class MixedListTest extends TestCase{
    
     public void testMixedListConstructor(){
        def list = [1,true, false, (1..14), "wombat"] as List
        MyMixedListClass myObject = new MyMixedListClass("tonnes of stuff", list)
        Dice myDice = new Dice("string", list)
     }
}

class MyMixedListClass{
    public MyMixedListClass(String str, java.util.List myList){
        
    }
}