noop:
	noop

build-and-push:
	docker build . -t yngvark/header-printer
	docker push yngvark/header-printer

run:
	h upgrade --install -f header-printer/values.yaml headerprinter header-printer
