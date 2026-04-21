# Advanced Programming Project Roadmap

## Assignment 1: Object-Oriented Programming. Inheritance and Interfaces.

### CSV File Reading

**Implementation**
- [x] `CSV` class created
  - [x] `readTable` method
  - [x] `readTableWithLabels` method
- [x] `Table` class created
  - [x] `headers` attribute
  - [x] `getRowAt` method
  - [X] `getColumnAt` method
- [x] `TableWithLabels` class created
  - [X] `getRowAt` method
  - [x] `getLabelAsInteger` method
- [x] `Row` class created
  - [x] `data` attribute
  - [x] `getData` method
- [x] `RowWithLabels` class created
  - [x] `label` attribute
  - [x] `getLabel` method

**Unit Tests**
- [x] `CSVTest`
- [X] `TableTest`
- [X] `TableWithLabelsTest`

### KNN (Recommendations-Nearest Neighbors)

**Implementation**
- [x] `KNN` class created
  - [x] `train` method  
  - [x] `estimate` method

**Unit Tests**
- [X] `KNNTest`


## Assignment 2: Object-Oriented Programming. Interfaces, Generic Types, and Exceptions

### Clustering with k-means
**Implementation**
- [x] `KMeans` class created
  - [x] `train` method
  - [x] `estimate` method
     
**Unit Tests**
- [x] `KMeansTest`

### Refactoring using a generic interface
**Implementation**
- [x] `Algorithms` interface created
  - [x] `KNN` class refactored
  - [x] `KMeans` class refactored

**Unit Tests**
- [x] `KMeansTest`

### Practical application of the Algorithms interface: a recommendation system.
**Implementation**
- [x] `RecSys` class created
  - [x] `train` method
  - [x] `initialise` method
  - [x] `recommend` method
### Definition and use of exceptions
**Implementation**
- [x] `InvalidClusterNumber` exception created
- [x] `LikedItemNotFoundException` exception created

**Unit Tests**
- [x] `RecSysTest`


## Assignment 3: Design patterns

### Strategy design pattern

### Design pattern Template method