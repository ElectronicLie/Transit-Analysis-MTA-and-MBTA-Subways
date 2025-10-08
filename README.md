# Analyzing subway systems through linear algebraic methods
My model aims to analyze the complicated relationships between stations and other stations, between stations and their services, and between services and other services to determine which service is the most central, a proxy for which one is the most essential to the travel between stations in the whole system.

## Processing the graphical layout of systems
Mta.java and T.java define the lines/services of the MTA's subway and the MBTA's T as an array of the names of stations that they go to. 
Toy.java defines a toy model that is used for testing.
Methods in TransitSystem.java are used to transform these definitions into a network of nodes and edges, where the nodes are stations and an edge between two nodes represents part of one or more lines that travel directly through both stations consecutively.

## Centrality of stations
A Markov chain is used to determine the 'centralities' of all stations that are at the intersection of two or more lines/services and, if suitable (depending on the size of the system), all terminal stations.
All stations through which only one line/service travels (typically deemed 'local') are excluded from the Markov chain because they have a negligble effect on station-line relationships.

## Centrality of lines/services
A matrix of 'stop-line' data is formed by setting the entry (_m_, _n_) to station _m_'s centrality value if line _n_ stops at it or 0 if line _n_ does not stop at it. This data is then mean-centered.
Principal component analysis is applied to this data to determine the 'centralities' of all lines/services and outputs results to a text file, where each axis represents a line (whose path has a measurable effect on the centrality of the stations it stops at) and the centralities are determined by the weighted average of all principal component eigenvectors by eigenvalue.
Results for the MTA can be seen in src/main/java/results.txt, where a more positive value correlates directly with a higher centrality. Thus far, my model determines the **3** train to be the most central.

## Plans to measure efficiency of systems
I plan to implement a formula that measures how well-connected the network of stations is using an adjacency matrix that represents the connections between all stations over a critical number of time steps such that you can travel from any station to any other. 
This formula will be used to determine the efficiency of the placement of lines/services (placement meaning the set of stations that the line/service serves) by dividing its output by the number of time steps taken in running the formula.

## Utilized packages
I created my own packages: linalg, malo, fractions, and polynomials. 
linalg is used for the more basic linear algebraic operations. The Apache Commons Math library is used for the more complicated ones, such as calculated eigenvectors and eigenvalues.
malo stands for Math, Array, and Logic Operations and is used for operations such as rounding and appending to arrays.
I developed fractions and polynomials in an effort to make my own algorithms to calculate eigenvectors and eigenvalues, but eventually decided to rely on Apache Commons.
