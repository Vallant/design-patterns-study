\defcounter {refsection}{0}\relax 
\addvspace {10\p@ }
\defcounter {refsection}{0}\relax 
\addvspace {10\p@ }
\defcounter {refsection}{0}\relax 
\addvspace {10\p@ }
\defcounter {refsection}{0}\relax 
\addvspace {10\p@ }
\defcounter {refsection}{0}\relax 
\contentsline {lstlisting}{\numberline {4.1}DBManager singleton class. Used for instantiating the implementation of the chosen interface.}{29}{lstlisting.4.1}
\defcounter {refsection}{0}\relax 
\contentsline {lstlisting}{\numberline {4.2}Code snippet on how a DBManager implementation instantiates its repositories.}{30}{lstlisting.4.2}
\defcounter {refsection}{0}\relax 
\contentsline {lstlisting}{\numberline {4.3}Code snippet showing how repositories are used in business code. The main model holds the single instance of the DBManager. Each sub-model accesses it by using a getter-method.}{30}{lstlisting.4.3}
\defcounter {refsection}{0}\relax 
\contentsline {lstlisting}{\numberline {4.4}Code snippet showing initialization code for the controller. The main model retrieves the main controller by calling a getter which is similarily implemented as the DBManager class shown in listing \ref {lst:dbmanager}. After that he calls a method that pairs the sub-model with the sub-controller, a code snippet showing this process can be seen in listing \ref {lst:pairing}.}{31}{lstlisting.4.4}
\defcounter {refsection}{0}\relax 
\contentsline {lstlisting}{\numberline {4.5}Code snipped showing pairing code between a sub-controller and a sub-view. The pairing between the model and controller layer works in a very similar way.}{32}{lstlisting.4.5}
\defcounter {refsection}{0}\relax 
\contentsline {lstlisting}{\numberline {4.6}Code snipped showing database access in the ad-hoc program version. All database code is placed into the corresponding data class, retrieval methods are static and take the corresponding DBManager as parameter. The reference to the DBManager is provided by a getter method of the main model, as in listing \ref {lst:db-access-bp}.}{32}{lstlisting.4.6}
\defcounter {refsection}{0}\relax 
\contentsline {lstlisting}{\numberline {4.7}Code snippet showing how the DBManager is instantiated. Again no interfaces but rather concrete implementations are used. The singleton class seen in listing \ref {lst:dbmanager} does not exist in the ad-hoc version.}{33}{lstlisting.4.7}
\defcounter {refsection}{0}\relax 
\contentsline {lstlisting}{\numberline {4.8}Code snippet showing a database access methods in a data class. The retrieval method takes arguments necessary for the query and the database-manager object as parameters and is static, while the update method is not static and therefore performs the operation on the current object instance.}{33}{lstlisting.4.8}
\defcounter {refsection}{0}\relax 
\contentsline {lstlisting}{\numberline {4.9}Code snippet showing pairing code of the ad-hoc version of the program. It is noticeable that the pairing method takes a concrete implementation rather than an interface as a parameter. This differs from the best practice version seen in \ref {lst:pairing} which takes only the interface as parameter.}{34}{lstlisting.4.9}
\defcounter {refsection}{0}\relax 
\contentsline {lstlisting}{\numberline {4.10}View Manager after implementation of a second GUI. Only a case branch has to be added.}{35}{lstlisting.4.10}
\defcounter {refsection}{0}\relax 
\contentsline {lstlisting}{\numberline {4.11}Code snipped showing login-code after implementing a second GUI. Because no interface but concrete implementations are used each time a view access is needed the program is required to check for which of the two GUIs is currently active. Controller methods of the best pratice version do not change in any way.}{36}{lstlisting.4.11}
\defcounter {refsection}{0}\relax 
\contentsline {lstlisting}{\numberline {4.12}DBManager implementation after support for a second database. Again only a branch of the switch-statement has to be added.}{37}{lstlisting.4.12}
\defcounter {refsection}{0}\relax 
\contentsline {lstlisting}{\numberline {4.13}Code of the login-process after implementing support of the second database in the ad-hoc version of the program. Again because the application does not rely on interfaces as in listing \ref {lst:controller-switch} it has to be differentiated each time database access is needed. The methods for database code are overloaded.}{38}{lstlisting.4.13}
\defcounter {refsection}{0}\relax 
\addvspace {10\p@ }
\defcounter {refsection}{0}\relax 
\addvspace {10\p@ }
\defcounter {refsection}{0}\relax 
\addvspace {10\p@ }
\defcounter {refsection}{0}\relax 
\addvspace {10\p@ }
\defcounter {refsection}{0}\relax 
\addvspace {10\p@ }
