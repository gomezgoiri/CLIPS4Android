(deftemplate person
	(slot name)
	(slot age (type INTEGER))
)

(deffacts people

	(person
		(name Alan)
		(age 30)
	)
	
	(person
		(name Ana)
		(age 16)
	)
	
	(person
		(name Jose)
		(age 58)
	)
)


; Increase age in one year
(defrule YetAnotherYear
	?fact <-(birthday ?nam)
	?person <-(person (name ?nam) (age ?ag))
	=>
	(retract ?fact)
	(modify ?person (age (+ ?ag 1)))
)