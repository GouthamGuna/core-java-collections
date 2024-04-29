# Java Memory Model

 * JMM is specification which guarantees visibility of fields (aka happens before) amidst reordering of instruction.

## Out of order execution:
   
 Out of order execution performance driven change done by compiler, JVM, CPU.
  
    a = 3;                                      
    b = 2;
    a = a + 1;
  
  **Instruction**
  
    -> Load a
    -> Set to 3
    -> Store a

    -> Load b
    -> Set 2
    -> Store b

    -> Load a
    -> Set to 4
    -> Store a

 **One Performance Improvement**

    a = 3;
    a = a + 3;

    b = 2;

 **Instruction**
 
    -> Load a
    -> Set to 3
    -> Set to 4
    -> Store a

    -> Load b
    -> Set to 2
    -> Store b

## Field Visibility

 In presence of multiple threads a.k.a. concurrency.

## Not only volatile

 Also,
    
 * synchronized
 * Locks,`synchronized Blocks`
 * Concurrent collections
 * Thread operations `(join, start)`

 final fields (special behavior);