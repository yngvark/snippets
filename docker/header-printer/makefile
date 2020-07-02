noop:
	noop

build-and-push:
	docker build . -t yngvark/header-printer
	docker push yngvark/header-printer

run:
	h upgrade --install -f header-printer/values.yaml headerprinter header-printer

run-test:
	h upgrade --install -f header-printer/values-test.yaml headerprinter header-printer

delete-and-run:
	k delete deploy headerprinter
	@make run
