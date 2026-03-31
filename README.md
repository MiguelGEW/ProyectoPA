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

### KNN (K-Nearest Neighbors)

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
- [x] `Algorithm` interface created
  - [x] `KNN` class refactored
  - [x] `KMeans` class refactored

**Unit Tests**
- [x] `KMeansTest`

### Practical application of the Algorithm interface: a recommendation system.
- [x] `RecSys` class created
  - [x] `train` method
  - [x] `initialise` method
  - [x] `recommend` method
### Definition and use of exceptions
- [x] `InvalidClusterNumber` exception created
- [x] `LikedItemNotFoundException` exception created

**Unit Tests**
- [x] `RecSysTest`
