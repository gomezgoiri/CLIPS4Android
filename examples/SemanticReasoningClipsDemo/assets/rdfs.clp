;taken from http://www.w3.org/TR/2002/WD-rdf-mt-20020429/

(defrule rdfs1
	(. ?x ?a ?y)
=>
	(assert (. ?a rdf:type rdf:Property))
)

(defrule rdfs2
	(. ?x ?a ?y)
	(. ?a rdfs:domain ?z)
=>
	(assert (. ?x rdf:type ?z ))
)

(defrule rdfs3
	(. ?x ?a ?u)
	(. ?a rdfs:range ?z)
=>
	(assert (. ?u rdf:type ?z))
)

(defrule rdfs4a
	(. ?x ?a ?y)
=>
	(assert (. ?x rdf:type rdfs:Resource))
)

;(defrule rdfs4aChecked
;    (. ?x ~rdfs:subClassOf ?y)
;    (. ?x ~rdfs:Class ?y)
;=>
;	(assert (. ?x rdf:type rdfs:Resource))
;)

(defrule rdfs4b
	(. ?x ?a ?u)
=>
	(assert (. ?u rdf:type rdfs:Resource))
)

(defrule rdfs5
	(. ?a rdfs:subPropertyOf ?b)
	(. ?b rdfs:subPropertyOf ?c)
=>
	(assert (. ?a rdfs:subPropertyOf ?c))
)

(defrule rdfs6
	(. ?x ?a ?y)
	(. ?a rdfs:subPropertyOf ?b)
=>
	(assert (. ?x ?b ?y))
)

(defrule rdfs7
	(. ?x rdf:type rdfs:Class)
=>
	(assert (. ?x rdfs:subClassOF rdfs:Resource))
)

(defrule rdfs8
	(. ?x rdfs:subClassOf ?y)
	(. ?y rdfs:subClassOf ?z)
=>
	(assert (. ?x rdfs:subClassOf ?z))
)

(defrule rdfs9
	(. ?x rdfs:subClassOf ?y)
	(. ?a rdf:type ?x)
=>
	(assert (. ?a rdf:type ?y))
)
