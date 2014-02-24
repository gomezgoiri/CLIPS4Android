;taken from http://www.w3.org/TR/owl2-profiles/#OWL_2_RL
; NOTAMENTAL: para lo de LIST mirar la pagina 46 del manual de CLIPS


;T(?c, owl:oneOf, ?x)
;LIST[?x, ?y1, ..., ?yn]
;->
;T(?y1, rdf:type, ?c)

;(defrule cls-oo
;	(. ?c owl:oneOf ?x)
;	(. ?x $?yn)
;=>
;	(assert (. ?yn rdf:type ?c))
;) 


;******************************************
; EQUALITY WITHOUT LIST OR FALSE
;******************************************

(defrule eq-ref
	(. ?s ?p ?o)
=>
	(assert (. ?s owl:sameAs ?s))
	(assert (. ?p owl:sameAs ?p))
	(assert (. ?o owl:sameAs ?o))
) 

(defrule eq-sym
	(. ?x owl:sameAs ?y)
=>
	(assert (. ?y owl:sameAs ?x))
) 

(defrule eq-trans
	(. ?x owl:sameAs ?y)
	(. ?y owl:sameAs ?z)
=>
	(assert (. ?x owl:sameAs ?z))
) 

(defrule eq-rep-s
	(. ?s owl:sameAs ?s1)
	(. ?s ?p ?o)
=>
	(assert (. ?s1 ?p ?o))
) 

(defrule eq-rep-p
	(. ?p owl:sameAs ?p1)
	(. ?s ?p ?o)
=>
	(assert (. ?s ?p1 ?o))
) 

(defrule eq-rep-o
	(. ?o owl:sameAs ?o1)
	(. ?s ?p ?o)
=>
	(assert (. ?s ?p ?o1))
) 

;******************************************
; PROPERTIES WITHOUT LIST OR FALSE
;******************************************

(defrule prp-dom
	(. ?p rdfs:domain ?c)
	(. ?x ?p ?y)
=>
	(assert (. ?x rdf:type ?c))
) 

(defrule prp-rng
	(. ?p rdfs:range ?c)
	(. ?x ?p ?y)
=>
	(assert (. ?y rdf:type ?c))
) 

(defrule prp-fp
	(. ?p rdf:type owl:FunctionalProperty)
	(. ?x ?p ?y1)
	(. ?x ?p ?y2)
=>
	(assert (. ?y1 owl:sameAs ?y2))
) 

(defrule prp-ifp
	(. ?p rdf:type owl:InverseFunctionalProperty)
	(. ?x1 ?p ?y)
	(. ?x2 ?p ?y)
=>
	(assert (. ?x1 owl:sameAs ?x2))
) 

(defrule prp-symp
	(. ?p rdf:type owl:SymmetricProperty)
	(. ?x ?p ?y)
=>
	(assert (. ?y ?p ?x))
)

(defrule prp-trp
	(. ?p rdf:type owl:TransitiveProperty)
	(. ?x ?p ?y)
	(. ?y ?p ?z)
=>
	(assert (. ?x ?p ?z))
)

(defrule prp-spo1
	(. ?p1 rdfs:subPropertyOf ?p2)
	(. ?x ?p1 ?y)
=>
	(assert (. ?x ?p2 ?y))
)

(defrule prp-eqp1
	(. p1 owl:equivalentProperty ?p2)
	(. ?x ?p1 ?y)
=>
	(assert (. ?x ?p2 ?y))
)

(defrule prp-eqp2
	(. ?p1 owl:equivalentProperty ?p2)
	(. ?x ?p2 ?y)
=>
	(assert (. ?x ?p1 ?y))
)

(defrule prp-inv1
	(. ?p1 owl:inverseOf ?p2)
	(. ?x ?p1 ?y)
=>
	(assert (. ?y ?p2 ?x))
)

(defrule prp-inv2
	(. ?p1 owl:inverseOf ?p2)
	(. ?x ?p2 ?y)
=>
	(assert (. ?y ?p1 ?x))
)

;******************************************
; CLASSES WITHOUT LIST OR FALSE
;******************************************

(defrule cls-svf1
	(. ?x owl:someValuesFromx ?y)
	(. ?x owl:onProperty ?p)
	(. ?u ?p ?v)
	(. ?v rdf:type ?y)
=>
	(assert (. ?u rdf:type ?x))
)

(defrule cls-svf2
	(. ?x owl:someValuesFrom owl:Thing)
	(. ?x owl:onProperty ?p)
	(. ?u ?p ?v)
=>
	(assert (. ?u rdf:type ?x))
)

(defrule cls-avf
	(. ?x owl:allValuesFrom ?y)
	(. ?x owl:onProperty ?p)
	(. ?u rdf:type ?x)
	(. ?u ?p ?v)
=>
	(assert (. ?v rdf:type ?y))
)

(defrule cls-hv1
	(. ?x owl:hasValue ?y)
	(. ?x owl:onProperty ?p)
	(. ?u rdf:type ?x)
=>
	(assert (. ?u ?p ?y))
)

(defrule cls-hv2
	(. ?x owl:hasValue ?y)
	(. ?x owl:onProperty ?p)
	(. ?u ?p ?v)
=>
	(assert (. ?u rdf:type ?x))
)

(defrule cls-maxc2
	(. ?x owl:maxCardinality "1"^^xsd:nonNegativeInteger)
	(. ?x owl:onProperty ?p)
	(. ?u rdf:type ?x)
	(. ?u ?p ?y1)
	(. ?u ?p ?y2) 
=>
	(assert (. ?y1 owl:sameAs ?y2))
)

(defrule cls-maxqc3
	(. ?x owl:maxQualifiedCardinality "1"^^xsd:nonNegativeInteger)
	(. ?x owl:onProperty ?p)
	(. ?x owl:onClass ?c)
	(. ?u rdf:type ?x)
	(. ?u ?p ?y1)
	(. ?y1 rdf:type ?c)
	(. ?u ?p ?y2)
	(. ?y2 rdf:type ?c) 
=>
	(assert (. ?y1 owl:sameAs ?y2))
)

(defrule cls-maxqc4
	(. ?x owl:maxQualifiedCardinality "1"^^xsd:nonNegativeInteger)
	(. ?x owl:onProperty ?p)
	(. ?x owl:onClass owl:Thing)
	(. ?u rdf:type ?x)
	(. ?u ?p ?y1)
	(. ?u ?p ?y2) 
=>
	(assert (. ?y1 owl:sameAs ?y2))
)

;******************************************
; Class Axioms WITHOUT LIST OR FALSE
;******************************************

(defrule cax-sco
	(. ?c1 rdfs:subClassOf ?c2)
	(. ?x rdf:type ?c1) 
=>
	(assert (. ?x rdf:type ?c2))
)

(defrule cax-eqc1
	(. ?c1 owl:equivalentClass ?c2)
	(. ?x rdf:type ?c1) 
=>
	(assert (. ?x rdf:type ?c2))
)

(defrule cax-eqc2
	(. ?c2 owl:equivalentClass ?c1)
	(. ?x rdf:type ?c2) 
=>
	(assert (. ?x rdf:type c1))
)

;******************************************
; Datatypes WITHOUT LIST OR FALSE
;******************************************

;******************************************
; Schema Vocabulary WITHOUT LIST OR FALSE
;******************************************

(defrule cls-oo
	(. )
=>
	(assert (. ))
)

(defrule cls-oo
	(. )
=>
	(assert (. ))
)

(defrule cls-oo
	(. )
=>
	(assert (. ))
)

(defrule cls-oo
	(. )
=>
	(assert (. ))
)

(defrule cls-oo
	(. )
=>
	(assert (. ))
)

(defrule cls-oo
	(. )
=>
	(assert (. ))
)

(defrule cls-oo
	(. )
=>
	(assert (. ))
)

(defrule cls-oo
	(. )
=>
	(assert (. ))
)